package com.game.isoball;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.game.isoball.gameEntities.CircularLauncherMechanism;
import com.game.isoball.gameEntities.GameEntity;
import com.game.isoball.gameObjects.Ball;
import com.game.isoball.gameObjects.GameObject;
import com.game.isoball.gameObjects.GameTile;
import com.game.isoball.gameObjects.ImageMap;
import com.game.isoball.util.MapUtil;
import com.game.isoball.util.NativePhysicsUtil;

/**
 * Created by Sean's Computer on 7/4/13.
 */
public class IBView extends SurfaceView implements SurfaceHolder.Callback,
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        OnScaleGestureListener {
/*    private int[][] mapGrid = new int[][]
            {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,1},
                    {1,0,1,1,0,0,2,2,2,0,0,1,1,0,0,0,1},
                    {1,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,1},
                    {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};*/


    private int[][] mapGrid = new int[][]
           {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,2,2,2,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

    private IBThread thread;
    private RectF levelRect = null;
    private int standardItemHeight = 47;
    private int standardItemWidth = 42;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    private Ball selectedBall = null;
    
    //Panning and zoom variables
    private Matrix currentMatrix = null;
    private Float zoomFactor = 1f;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private Float zoomX = 0f;
    private Float zoomY = 0f;
    private Float zoomSensitivity = .5f;
    float[] maxPointsMapped = new float[4];
    
    public IBView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = getHolder();
        
        gestureDetector = new GestureDetector(context, this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        holder.addCallback(this);
        
        thread = new IBThread(holder, context, new Handler() {
            public void handleMessage(Message m) {
            }
        });

        setFocusable(true);
        
        NativePhysicsUtil.InitializeWorld();
        ImageMap.generateMap(getContext());
        gameObjects = MapUtil.generateTileGrid(mapGrid);

        for(GameEntity gameEntity : MapUtil.entities) {
            for(GameTile gameTile: gameEntity.raisedTiles) {
                gameObjects.add(gameTile);
            }
        }

        generateLevelRect();
    }

    private void generateLevelRect() {
        float rectWidth = (mapGrid.length + mapGrid[0].length) * MapUtil.TILE_WIDTH * .5f;
        float rectHeight = (mapGrid.length + mapGrid[0].length) * MapUtil.TILE_DEPTH * .5f;

        levelRect = new RectF(0,0, rectWidth, rectHeight);
        validateRect();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.mRun = true;
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.mRun = false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
    	float[] point = new float[]{motionEvent.getX(), motionEvent.getY(),
    				0f, 0f};
    	Matrix tapMatrix = new Matrix(currentMatrix);
    	GameObject gameObject = null;
    	
    	tapMatrix.invert(tapMatrix);
    	tapMatrix.mapPoints(point);
    	
    	thread.setPaused();
    	
    	gameObject = getGameObjectfromPoint((int)point[0], (int)point[1]);
    
    	if(gameObject instanceof Ball) {
    		Ball ball = (Ball)gameObject;    		
    		Log.d("TapUp", "Ball type " + String.valueOf(ball.ballType) + " at " +
    				String.valueOf(ball.tileX) + "," + String.valueOf(ball.tileY));
    		selectedBall = ball;
    	} else if (gameObject != null) {
    		Log.d("TapUp", gameObject.getClass().getName() + " tapped.");
    	} else {
    		Log.d("TapUp", "No object tapped");
    	}
    	
    	thread.setUnpaused();    	
        return false;
    }
    
    public GameObject getGameObjectfromPoint(float x, float y) {
    	RectF currentRect = null;
    	GameObject currentObject = null;
    	Bitmap currentBitmap = null;
    	int deltaX = 0;
    	int deltaY = 0;

    	for(int index = gameObjects.size() - 1; index >= 0; index--) {
    		currentObject = gameObjects.get(index);
    		currentBitmap = currentObject.getBitmap();
    		
    		currentRect = new RectF(currentObject.screenX, currentObject.screenY,
    				currentBitmap.getWidth() + currentObject.screenX,
    				(currentBitmap.getHeight() + currentObject.screenY));
    		
    		if(!currentRect.contains(x, y)) {
    			continue;
    		}
    		
    		deltaX = (int)(x - currentObject.screenX);
    		deltaY = (int)(y - currentObject.screenY);
    		
    		if(currentBitmap.getPixel(deltaX, deltaY) == Color.TRANSPARENT) {
    			continue;
    		}
    		
    		return currentObject;    		
    	}
    	
    	return null;
    }
    
    private void checkforSimiliarBallTypes(Ball ball, ArrayList<GameObject> similiarBalls) {
    	long[] touchingIds = NativePhysicsUtil.GetTouching(ball.id);
    	
    	similiarBalls.add(ball);
    	
    	for(int index = 0; index < touchingIds.length; index++) {
    		//Log.d("isoball", "Current Id is " +  String.valueOf(touchingIds[index]));
    		GameObject gameObject = GetGameObjectById(touchingIds[index]);
    		
    		if(ball == null || similiarBalls.contains(gameObject) ||
    				(gameObject instanceof Ball && ((Ball)gameObject).ballType != ball.ballType) ||
    				!(gameObject instanceof Ball)) {
    			continue;
    		}
    		
    		checkforSimiliarBallTypes((Ball)gameObject, similiarBalls);
    	}
    }
    
    private GameObject GetGameObjectById(long id) {
    	for(GameObject gameObject : gameObjects) {
    		if(gameObject.id == id) {
    			return gameObject;
    		}
    	}
    	
    	return null;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2,
                            float distanceX,
                            float distanceY) {
    	float deltaX = distanceX;
    	float deltaY = distanceY;
    	RectF rectF = new RectF(levelRect);
    	
    	deltaX = deltaX / zoomFactor;
    	deltaY = deltaY / zoomFactor;
    	
    	rectF.offset(-deltaX, -deltaY);
    	
        levelRect = correctScroll(currentMatrix, rectF);

        return false;
    }
    
	public RectF correctScroll(Matrix matrix, RectF rectF) {
		RectF rectLocal = new RectF(rectF);
		Matrix invertMatrix = new Matrix();
		float[] rectPoints = new float[] {rectLocal.left, rectLocal.top, rectLocal.right, rectLocal.bottom};
		float[] screenPoints = new float[] {0,0, getWidth(), getHeight()};
		
		matrix.mapPoints(rectPoints);
		matrix.invert(invertMatrix);
		invertMatrix.mapPoints(screenPoints);
		
		float mLeft = rectPoints[0];
		float mTop = rectPoints[1];
		float mRight = rectPoints[2];
		float mBottom = rectPoints[3];			
				
		if(rectLocal.width() * zoomFactor < getWidth()) {
			rectLocal.offsetTo((getWidth() - rectLocal.width()) / 2, rectLocal.top);
		} else if(mLeft > 0) {			
			rectLocal.offset(-(rectLocal.left - screenPoints[0]), 0);
		} else if(mRight < getWidth()) {
			rectLocal.offset(screenPoints[2] - rectLocal.right, 0);
		}
		
		if(rectLocal.height() * zoomFactor < getHeight()) {
			rectLocal.offsetTo(rectLocal.left, (getHeight() - rectLocal.height()) / 2);
		} else if(mTop > 0) {
			rectLocal.offset(0, -(rectLocal.top - screenPoints[1]));
		} else if(mBottom < getHeight()) {
			rectLocal.offset(0, screenPoints[3] - rectLocal.bottom);
		}

		return rectLocal;
	}	

    private void validateRect() {
        if(levelRect.width() < getWidth() * zoomFactor) {
            levelRect.offsetTo((getWidth() - levelRect.width()) / 2, levelRect.top);
        } else {
            if(levelRect.right < getWidth()) {
                levelRect.offsetTo(getWidth() - levelRect.width(), levelRect.top);
            }

            if(levelRect.left > 0) {
                levelRect.offsetTo(0, levelRect.top);
            }
        }

        if(levelRect.height() < getHeight() * zoomFactor) {
            levelRect.offsetTo(levelRect.left,(getHeight() - levelRect.height()) / 2);
        } else {
            if(levelRect.bottom < getHeight()) {
                levelRect.offsetTo(levelRect.left, getHeight() - levelRect.height());
            }

            if(levelRect.top > 0) {
                levelRect.offsetTo(levelRect.left, 0);
            }
        }
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class IBThread extends Thread {    	
    	private volatile boolean paused = false;
        public SurfaceHolder mSurfaceHolder;
        private Handler mHandler;
        public boolean mRun = false;
        private Context context;

        public IBThread(SurfaceHolder surfaceHolder,Context context, Handler handler) {
            this.context = context;

            mSurfaceHolder = surfaceHolder;
        }
        
        public void setPaused() {
        	paused = true;
        }
        
        public void setUnpaused() {
        	paused = false;
        	synchronized (mSurfaceHolder) {
				mSurfaceHolder.notifyAll();
			}
        }

        public void run() {
        	while (mRun) {
        		Canvas c = null;

        		try {
        			c = mSurfaceHolder.lockCanvas();
        			synchronized (mSurfaceHolder) {
        				float screenX = levelRect.left + (mapGrid.length * MapUtil.TILE_WIDTH * .5f);
        				
        				if(selectedBall != null) {
        					ArrayList<GameObject> similiarBalls = new ArrayList<GameObject>();
        					
        					checkforSimiliarBallTypes(selectedBall, similiarBalls);
        					
        					Log.d("isoball", "Length of chain is " +  String.valueOf(similiarBalls.size()));
        					
        					selectedBall = null;
        				}
        				
        				for(GameEntity entity : MapUtil.entities) {
        					if(!entity.updateState()) {
        						continue;
        					}

        					if(entity instanceof CircularLauncherMechanism) {
        						Ball newBall = ((CircularLauncherMechanism)entity).getNewBall();
        						
        						gameObjects.add(newBall);
        					}
        				}

        				NativePhysicsUtil.UpdateWorld();
        				MapUtil.updateGameObjects(screenX, levelRect.top, gameObjects);
        				MapUtil.DepthSort(gameObjects);
        				doDraw(c, gameObjects);
        			}
        			
    				while(paused) {
    						mSurfaceHolder.wait();
        			}
        			
        		} catch (Exception e) {
        			e.printStackTrace();
        		} finally {
        			if (c != null) {
        				mSurfaceHolder.unlockCanvasAndPost(c);
        			}
        		}
        	}
        }

        public void doDraw(Canvas c, ArrayList<GameObject> drawableGameObjects) {
            Matrix copy = null;
        	
            
        	if(c == null) {
                return;
            }

        	c.save();
        	c.scale(zoomFactor, zoomFactor, zoomX, zoomY);
        	currentMatrix = c.getMatrix();
        	copy = new Matrix(currentMatrix);
        	copy.invert(copy);
        	copy.mapPoints(maxPointsMapped);
        	
            c.drawColor(Color.BLACK);

            int ballCount = 0;
            
            for(GameObject gameObject : drawableGameObjects) {
                Bitmap bitmap = gameObject.getBitmap();
                int deltaScreenY = bitmap.getHeight() - standardItemHeight;

                c.drawBitmap(gameObject.getBitmap(), gameObject.screenX, gameObject.screenY - deltaScreenY, null);
            }
            
            c.restore();
        }
    }

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float deltaSpan = (detector.getCurrentSpan() - detector.getPreviousSpan()) * zoomSensitivity;
		float zoomRatio = zoomFactor;
		Matrix testMatrix = new Matrix();
		
		zoomFactor = zoomFactor + deltaSpan / 100;
		
		if (zoomFactor < 1) {
			zoomFactor = 1f;			
		} else if (zoomFactor > 4) {
			zoomFactor = 4f;
		}
		
		zoomRatio = zoomFactor / zoomRatio;
		
		zoomX = detector.getFocusX();
		zoomY = detector.getFocusY();
		testMatrix.postScale(zoomFactor, zoomFactor, zoomX, zoomY);
		levelRect = correctScroll(testMatrix, levelRect);
		
		return false;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		float deltaSpan = detector.getCurrentSpan() - detector.getPreviousSpan();
		
		zoomFactor = zoomFactor + deltaSpan / 100;
		
		if (zoomFactor < 1) {
			zoomFactor = 1f;
		} else if (zoomFactor > 4) {
			zoomFactor = 4f;
		}
		
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}
}
