package org.cocoa4android.util;

import java.util.ArrayList;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.cg.CGSize;
import org.cocoa4android.ui.UIControl;
import org.cocoa4android.ui.UIControl.UIControlEvent;
import org.cocoa4android.ui.UIPageControl;
import org.cocoa4android.ui.UIScrollView;
import org.cocoa4android.ui.UIScrollView.UIScrollViewDelegate;
import org.cocoa4android.ui.UIView;

public class CAPageView extends UIView implements UIScrollViewDelegate{
	
	UIScrollView scrollView;
    ArrayList<UIView> views;
    boolean pageControlUsed;
    int kNumberOfPages;
    boolean isTouchMoved;
    UIControl mask;
    
    private UIPageControl pageControl;
    public UIPageControl pageControl() {
		return pageControl;
	}

	public void setPageControl(UIPageControl pageControl) {
		this.pageControl = pageControl;
	}
	private PGPageViewDatasouce datasource;
    private PGPageViewDelegate delegate;
    private boolean showPageControl;
    private boolean isTouchMaskEnabled;
    
    
    public CAPageView(CGRect frame){
    	super(frame);
    	scrollView = new UIScrollView(new CGRect(0,0,frame.size().width(),frame.size().height()));
    	scrollView.setShowsHorizontalScrollIndicator(false);
    	scrollView.setShowsVerticalScrollIndicator(false);
    	this.addSubview(scrollView);
    	
    	pageControl = new UIPageControl();
    	
    	mask = new UIControl(new CGRect(0,0,frame.size().width(),frame.size().height()));
    	mask.addEventListener(this, "didSelectOnView", UIControlEvent.UIControlEventTouchUpInside);
    }
    
    public void scrollToPage(int page){
    	 if (page<kNumberOfPages) {
    		this.loadScrollViewWithPage(page-1);
    		this.loadScrollViewWithPage(page);
    		this.loadScrollViewWithPage(page+1);
 	        
 	        // update the scroll view to the appropriate page
 	        CGRect frame = this.frame();
 	        
 	        scrollView.scrollRectToVisible(new CGRect(frame.size().width()*page,frame.origin().y(),frame.size().width(),frame.size().height()), true);
 	        // Set the boolean used when scrolls originate from the UIPageControl. See scrollViewDidScroll: above.
 	        pageControlUsed = true;
 	    }
    }
    public void reloadData(){
    	views.clear();
    	this.setDatasource(this.datasource);
    }
    
    public void didSelectOnView(){
    	if(delegate!=null){
    		delegate.didSelectAtPage(this, pageControl.currentPage());
    	}
    }
    
    
	public PGPageViewDatasouce getDatasource() {
		return datasource;
	}

	public void setDatasource(PGPageViewDatasouce datasource) {
		this.datasource = datasource;
		if(datasource!=null){
			kNumberOfPages = datasource.numberOfPages();
			views = new ArrayList<UIView>(kNumberOfPages);
			for (int i = 0; i < kNumberOfPages; i++)
	        {
				views.add(null);
	        }
			CGRect frame = this.frame();
			CGSize size = new CGSize(frame.size().width()*kNumberOfPages,frame.size().height());
			scrollView.setContentSize(size);
			scrollView.setDelegate(this);
			scrollView.setPageEnabled(true);
			pageControl.setNumberOfPages(kNumberOfPages);
	        pageControl.setCurrentPage(0);
	        
	        this.loadScrollViewWithPage(0);
	        this.loadScrollViewWithPage(1);
		}
	}
	public void loadScrollViewWithPage(int page){
		if (page < 0)
	        return;
	    if (page >= kNumberOfPages)
	        return;
	    UIView view = views.get(page);
	    if(view==null){
	    	view = datasource.viewAtPage(this, page);
	    	views.set(page, view);
	    }
	    if(view!=null && view.superView()==null){
	    	CGRect frame = this.frame();
	    	CGRect viewFrame = view.frame();
	    	
	    	float x = frame.size().width()*page + (frame.size().width()-viewFrame.size().width())/2;
	    	float y = (frame.size().height()-viewFrame.size().height())/2;
	    	view.setFrame(new CGRect(x,y,viewFrame.size().width(),viewFrame.size().height()));
	    	scrollView.addSubview(view);
	    }
	    if (isTouchMaskEnabled&&page==0&&mask.superView()==null) {
	    	scrollView.addSubview(mask);
	    }
	}

	public PGPageViewDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(PGPageViewDelegate delegate) {
		this.delegate = delegate;
	}

	
	

	@Override
	public void scrollViewDidScroll(UIScrollView scrollView) {
	}

	@Override
	public void scrollViewWillBeginDragging(UIScrollView scrollView) {
		// TODO Auto-generated method stub
		pageControlUsed = false;
	}

	@Override
	public void scrollViewDidEndDecelerating(UIScrollView scrollView) {
		// TODO Auto-generated method stub
		pageControlUsed = false;
		
		CGRect frame = mask.frame();
		
		float pageWidth = scrollView.frame().size().width();
		int page = (int) (Math.floor((scrollView.contentOffSet().x()-pageWidth/2)/pageWidth)+1);
		pageControl.setCurrentPage(page);
		
		frame.origin().setX(page*this.frame().size().width());
	    mask.setFrame(frame);
	    
	    
	    
	    scrollView.bringSubviewToFront(mask);
		if(delegate!=null){
			delegate.didScrollToPage(this, pageControl.currentPage());
		}
		this.loadScrollViewWithPage(page-1);
        this.loadScrollViewWithPage(page);
        this.loadScrollViewWithPage(page+1);
	}

	public boolean isTouchMaskEnabled() {
		return isTouchMaskEnabled;
	}

	public void setTouchMaskEnabled(boolean isTouchMaskEnabled) {
		this.isTouchMaskEnabled = isTouchMaskEnabled;
	}
	
	
	public boolean isShowPageControl() {
		return showPageControl;
	}

	public void setShowPageControl(boolean showPageControl) {
		this.showPageControl = showPageControl;
	}


	public interface PGPageViewDatasouce{
		public UIView viewAtPage(CAPageView pageView,int page);
		public int numberOfPages(); 
	}
	public interface PGPageViewDelegate{
		public void didSelectAtPage(CAPageView pageView,int page);
		public void didScrollToPage(CAPageView pageView,int page);
	}
}
