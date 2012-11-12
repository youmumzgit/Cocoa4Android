package org.cocoa4android.ui;

import java.util.ArrayList;
import java.util.List;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSIndexPath;
import org.cocoa4android.ui.UITableViewCell.UITableViewCellSeparatorStyle;
import org.cocoa4android.ui.UITableViewCell.UITableViewCellShapeType;

import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

public class UITableView extends UIView {
	private ListView listView = null;
	private refreshableAdapter adapter = null;
	private UITableViewDataSource dataSource = null;
	private UITableViewDelegate delegate = null;
	
	private UITableViewStyle style;
	private boolean isGrouped;
	
	private List<NSIndexPath> mappingList = null;//mapping position to indexPath
	private List<UIView> cellsList = null;
	
	private UIView selectedView = null;
	private NSIndexPath selectedIndexPath = null;
	
	
	public UITableView() {
		this(UITableViewStyle.UITableViewStylePlain);
	}
	
	public UITableView(UITableViewStyle style) {
		this.style = style;
		listView = new ListView(context);
		listView.setBackgroundColor(Color.GRAY);
		listView.setDrawingCacheBackgroundColor(Color.RED);
		listView.setCacheColorHint(0);
		this.setView(listView);
		isGrouped = (style == UITableViewStyle.UITableViewStyleGrouped) ? true : false;
		if(isGrouped) {
			listView.setPadding((int)(10*density), (int)(10*density), (int)(10*density), (int)(25*density));
		}
		adapter = new refreshableAdapter(mappingList);
		listView.setAdapter(adapter);
		listView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> listView, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				/*
		        if (!listView.isFocused())
		        {
		            // listView.setItemsCanFocus(false);

		            // Use beforeDescendants so that the EditText doesn't re-take focus
		            listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
		            listView.requestFocus();
		        }else{
		        	 listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
		        }
			    
				
				
				if(selectedView != null){
					selectedView.setBackgroundColor(UIColor.getWhiteColor());
					listView.invalidate();
					if (delegate != null) {
						delegate.disDeselectRowAtIndexPath(UITableView.this, selectedIndexPath);
					}
				}
				selectedView = cellsList.get(position);
				selectedIndexPath = mappingList.get(position);
				selectedView.setBackgroundColor(UIColor.getBlueColor());
				listView.invalidate();
				if(delegate != null) {
					delegate.didSelectRowAtIndexPath(UITableView.this, selectedIndexPath);
				}
				*/
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				//listView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
			}
		});
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
				
				if(selectedView != null){
					selectedView.setBackgroundColor(UIColor.getWhiteColor());
					listView.invalidate();
					if (delegate != null) {
						delegate.disDeselectRowAtIndexPath(UITableView.this, selectedIndexPath);
					}
				}
				selectedView = cellsList.get(position);
				selectedIndexPath = mappingList.get(position);
				selectedView.setBackgroundColor(UIColor.getBlueColor());
				listView.invalidate();
				if(delegate != null) {
					delegate.didSelectRowAtIndexPath(UITableView.this, selectedIndexPath);
				}
				
			}
		});
		
		listView.setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
	}
	public UITableView(CGRect frame) {
		this();
		this.setFrame(frame);
	}
	public UITableViewStyle style() {
		return style;
	}
	private ArrayList<NSIndexPath> initMapping() {
		if(dataSource != null && delegate != null) {
			mappingList = new ArrayList<NSIndexPath>();
			cellsList = new ArrayList<UIView>();
			int numberOfSection = dataSource.numberOfSectionsInTableView(this);
			int numberOfRowInSection = 0;
			NSIndexPath indexPath = null;
			for(int section = 0;section < numberOfSection;section++) {
				mappingList.add(new NSIndexPath(section,-1));//header
				cellsList.add(delegate.viewForHeaderInSection(this, section));//header
				numberOfRowInSection = dataSource.numberOfRowsInSection(this, section);
				for(int row = 0;row < numberOfRowInSection;row++) {
					indexPath = new NSIndexPath(section, row);
					mappingList.add(indexPath);
					cellsList.add(dataSource.cellForRowAtIndexPath(this, indexPath));
				}
				mappingList.add(new NSIndexPath(section,-2));//footer
				cellsList.add(delegate.viewForFooterInSection(this, section));//footer
			}
		}
		return (ArrayList<NSIndexPath>) mappingList;
	}
	public void reloadData() {
		this.initMapping();
		adapter.mappingList  = mappingList;
		adapter.notifyDataSetChanged();
		listView.invalidateViews();
	}
	
	public void setDataSource(UITableViewDataSource dataSource) {
		this.dataSource = dataSource;
		this.initMapping();
	}
	public void setDelegate(UITableViewDelegate delegate) {
		this.delegate = delegate;
		this.initMapping();
	}
	
	public void selectRowAtIndexPath(NSIndexPath indexPath) {
		if (cellsList != null) {
			int position = mappingList.indexOf(indexPath);
			if (selectedView != null) {
				selectedView.setBackgroundColor(UIColor.getWhiteColor());
				listView.invalidate();
			}
			selectedIndexPath = indexPath;
			selectedView = cellsList.get(position);
		}
	}
	public void deselectRowAtIndexPath(NSIndexPath indexPath) {
		if (indexPath.isEqual(selectedIndexPath)) {
			selectedView.setBackgroundColor(UIColor.getWhiteColor());
			listView.invalidate();
			selectedIndexPath = null;
			selectedView = null;
		}
	}
	
	public void setSeparatorStyle(UITableViewCellSeparatorStyle style) {
		switch(style) {
		case UITableViewCellSeparatorStyleNone:
			listView.setDividerHeight(0);
			break;
		case UITableViewCellSeparatorStyleSingleLine:
			listView.setDividerHeight(1);
			break;
		case UITableViewCellSeparatorStyleSingleLineEtched:
			break;
		}
	}
	
	public interface UITableViewDataSource {
		int numberOfRowsInSection(UITableView tableView,int section);
		UITableViewCell cellForRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		
		int numberOfSectionsInTableView(UITableView tableView);
	}
	
	public interface UITableViewDelegate {
		float heightForRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		float heightForHeaderInSection(UITableView tableView,int section);
		float heightForFooterInSection(UITableView tableView,int section);
		UIView viewForHeaderInSection(UITableView tableView,int section);
		UIView viewForFooterInSection(UITableView tableView,int section);
		void didSelectRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		void disDeselectRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
	}
	
	public enum UITableViewStyle {
		UITableViewStylePlain,                  // regular table view
	    UITableViewStyleGrouped                 // preferences style table view
	}
	
	public class refreshableAdapter extends BaseAdapter{
		public List<NSIndexPath> mappingList;
		public refreshableAdapter(List<NSIndexPath> mappingList){
			this.mappingList = mappingList;
		}
		
		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			
		}
		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			
		}
		@Override
		public boolean isEmpty() {
			return false;
		}
		
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public int getViewTypeCount() {
			return 2;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(dataSource != null && delegate != null) {
				NSIndexPath indexPath = mappingList.get(position);
				if(indexPath.row() < 0) {
					if(indexPath.row() == -1) {//header
						UIView view = cellsList.get(position);
						float height = delegate.heightForHeaderInSection(UITableView.this, indexPath.section());
						if(height <= 0 || view == null) {
							view = new UIView();
						}
						view.setBackgroundColor(UIColor.getClearColor());
						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*density));
						view.getView().setLayoutParams(params);
						return view.getView();
					}
					else if(indexPath.row() == -2) {//footer
						UIView view = cellsList.get(position);
						float height = delegate.heightForFooterInSection(UITableView.this, indexPath.section());
						if(height <= 0 || view == null) {
							view = new UIView();
						}
						view.setBackgroundColor(UIColor.getClearColor());
						AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*density));
						view.getView().setLayoutParams(params);
						return view.getView();
					}
				}
				else {
					UITableViewCell cell = (UITableViewCell) cellsList.get(position);
					float height = delegate.heightForRowAtIndexPath(UITableView.this, indexPath);
					cell.setHeight(height);
					int section = indexPath.section();
					int row = indexPath.row();
					int numberOfRowsInSection = dataSource.numberOfRowsInSection(UITableView.this, section);
					if (row == 0 && row == numberOfRowsInSection-1) {
						cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeAllRound);
					}
					else if (row == 0) {
						cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeTopRound);
					}
					else if (row == numberOfRowsInSection-1) {
						cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeBottomRound);
					}
					else {
						cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeNoRound);
					}
					return cell.getView();
				}
			}
			return null;
		}
		
		@Override
		public int getItemViewType(int position) {
			return 0;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public int getCount() {
			if(dataSource != null) {
				if(mappingList == null) {
					mappingList = initMapping();
				}
				
				return mappingList.size();
			}
			return 0;
		}
		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}
		@Override
		public boolean isEnabled(int position) {
			if(mappingList == null) {
				mappingList = initMapping();
			}
			NSIndexPath indexPath = mappingList.get(position);
			if(indexPath.row() < 0) {
				return false;
			}
			return true;
		}
	}
}
