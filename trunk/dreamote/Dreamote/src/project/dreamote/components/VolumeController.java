package project.dreamote.components;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class VolumeController extends ImageView implements OnTouchListener{
	public static final int MAX_VOLUME = 100;
	
	private OnVolumeChangeListener listener;
	private Context context;
	
	private List<Integer> drawableIds;
	
	private int currVolume = 0;
	private int nrOfSteps = 0;
	private int stepHeight = 0;
	
	public VolumeController(Context context) {
		super(context);
		this.context = context;
		this.setOnTouchListener(this);
	}
	
	public VolumeController(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setOnTouchListener(this);
	}
	
	private void updateBackgroundImage() {
		this.setBackgroundDrawable(context.getResources().getDrawable(drawableIds.get(currVolume)));
		invalidate();
	}
	
	public void setBackgroundImages(List<Integer> drawableIds) {
		this.drawableIds = drawableIds;
		nrOfSteps = drawableIds.size();
		updateBackgroundImage();
	}
	
	public void setOnVolumeChangeListener(OnVolumeChangeListener listener) {
		this.listener = listener;
	}
	
	public void setVolume(int volume) {
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int height = v.getHeight();
		int yPos = height-(int)event.getY();
		stepHeight = height/nrOfSteps;
	    int newVolume = currVolume;
		for(int i = 1; i <= nrOfSteps; i++) {
			if(yPos <= i*stepHeight){
				newVolume = i-1;
				break;
			}
		}
		
		newVolume = newVolume >= drawableIds.size() ? drawableIds.size()-1 : newVolume;
		if(newVolume != currVolume) {
			currVolume = newVolume;
			updateBackgroundImage();
			notifyListener();
		}
		return false;
	}
	
	private void notifyListener() {
		int volumeStepValue = MAX_VOLUME/nrOfSteps;
		listener.onVolumeChange(currVolume*volumeStepValue);
	}
}
