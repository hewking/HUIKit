package com.hewking.androidview.creamepreview

import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hewking.customviewtest.R
import com.hewking.customviewtest.util.v
import java.io.IOException


/**
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/3/2
 * 修改人员：hewking
 * 修改时间：2018/3/2
 * 修改备注：
 * Version: 1.0.0
 */
class CameraPraviewActivity : AppCompatActivity() ,SurfaceHolder.Callback{

    private var camera: Camera? = null
    private var isPreview = false


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        if(camera != null){
            if(isPreview){//正在预览
                camera?.stopPreview();
                camera?.release();
            }
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            // Camera,open() 默认返回的后置摄像头信息
            camera = Camera.open()//打开硬件摄像头，这里导包得时候一定要注意是android.hardware.Camera
            //此处也可以设置摄像头参数
            /**
             * WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
             * Display display  = wm.getDefaultDisplay();//得到当前屏幕
             * Camera.Parameters parameters = camera.getParameters();//得到摄像头的参数
             * parameters.setPictureFormat(PixelFormat.RGB_888);//设置照片的格式
             * parameters.setJpegQuality(85);//设置照片的质量
             * parameters.setPictureSize(display.getHeight(), display.getWidth());//设置照片的大小，默认是和     屏幕一样大
             * camera.setParameters(parameters);
             */
            //设置角度，此处 CameraId 我默认 为 0 （后置）
            // CameraId 也可以 通过 参考 Camera.open() 源码 方法获取
//            setCameraDisplayOrientation(this@MainActivity, 0, camera)
            camera?.setPreviewDisplay(holder)//通过SurfaceView显示取景画面
            camera?.startPreview()//开始预览
            isPreview = true//设置是否预览参数为真
        } catch (e: IOException) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_preview_laayout)

        val surfaceView = v<SurfaceView>(R.id.surfview)

        val holder = surfaceView.holder

        holder.addCallback(this)


    }


}

