package TagButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.tagbutton.R;

public class TagButton extends View{
	
	//分别为开、关、浮动按钮的贴图
	private Bitmap bg_on, bg_off, switch_button;
	//按下时的X坐标，当前坐标
	private float down_x, now_x;
	//开关状态
	private boolean status = false;
	//是不是在滑动状态
	private boolean onSlip = false;
	
	private OnSwitchChangListener onSwitchChangListener;
	
	//初始化
	public void init()
	{
		down_x = 0;
		now_x = 0;
		bg_on = BitmapFactory.decodeResource(getResources(), R.drawable.switch_on);
		bg_off = BitmapFactory.decodeResource(getResources(), R.drawable.switch_off);
		switch_button = BitmapFactory.decodeResource(getResources(), R.drawable.switch_button);
	}

	public TagButton(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	
	public TagButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);  
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);  
        
        int width = 0;  
        int height = 0;
        
        if(widthMode == MeasureSpec.EXACTLY)
        {
        	width = widthSize;
        }
        
        if (widthMode == MeasureSpec.AT_MOST) {  
            // Parent has told us how big to be. So be it.  
            width = bg_off.getWidth();  
        } 
        if (heightMode == MeasureSpec.AT_MOST)
        {
        	height = bg_off.getHeight();
        }
        setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch(ev.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			if (ev.getX() > bg_off.getWidth())
			{
				return false;
			}
			else if (down_x == 0)
			{
				down_x = ev.getX();
				now_x = down_x;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE:
		{
			now_x = ev.getX();
			onSlip = true;
			break;
		}
		case MotionEvent.ACTION_UP:
		{
			onSlip = false;
			if (ev.getX() >= (bg_on.getWidth() / 2))
			{
				status = true;
				now_x = bg_on.getWidth() - switch_button.getWidth();
			}
			else {
				status = false;
				down_x = 0;
			}
			
			if (onSwitchChangListener != null)
			{
				onSwitchChangListener.onSwitchChange(TagButton.this, status);
			}
			
			break;
		}
		default:
		{
			//
		}
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float distance = 0;
		
		//根据now_x设置背景，开、关的状态
		int half_width = getWidth() / 2;
		if (now_x < half_width)
		{
			canvas.drawBitmap(bg_off, matrix, paint);
		}
		else {
			canvas.drawBitmap(bg_on, matrix, paint);
		}
		if (onSlip)
		{
			if (now_x >= bg_on.getWidth())
			{
				distance = bg_on.getWidth() - switch_button.getWidth() / 2;
			}
			else {
				distance = now_x - switch_button.getWidth();
			}
		}
		else {
			if (status)
			{
				distance = bg_on.getWidth() - switch_button.getWidth();
			}
			else {
				distance = -3;
			}
		}
		if (distance < 0)
		{
			distance = -3;
		}
		else if (distance > bg_on.getWidth() - switch_button.getWidth()) {
			distance = bg_on.getWidth() - switch_button.getWidth();
		}
		canvas.drawBitmap(switch_button, distance, 0,  paint);
	}

	public void setChecked(boolean checked)
	{
		if (checked)
		{
			now_x = bg_off.getWidth();
		}
		else {
			now_x = -3;
		}
		status = checked;
		
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
		super.setOnClickListener(l);
	}

	public boolean getStatus()
	{
		return status;
	}
	
	public void setOnChangListener(OnSwitchChangListener listener)
	{
		onSwitchChangListener = listener;
	}
	
	public interface OnSwitchChangListener
	{
		public void onSwitchChange(TagButton tagButton, boolean isChecked);
	}
}
