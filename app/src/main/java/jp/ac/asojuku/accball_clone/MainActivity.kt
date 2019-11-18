package jp.ac.asojuku.accball_clone

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
        , SensorEventListener, //センサーの反応を受け取るためのインターフェース
        SurfaceHolder.Callback{     //Surfaceviewを実装するのための窓口Holderのコールバックインターフェース

    //instance property
    //SurfaceViewの幅と高さの初期値を設定
    private var surfaceWidth:Int = 0;
    private var surfaceHeight:Int = 0;
    //ボールの半径
    private val radius = 50.0f;
    //ボールの移動量を計算するための係数
    private val coef = 1000.0f;

    //ボールの座標
    //X座標
    private var ballX:Float = 0f;
    //Y座標
    private var ballY:Float = 0f;
    //X座標の重力加速度
    private var vx:Float = 0f;
    //Y座標の重力加速度
    private var vy:Float = 0f;

    //前回の時間を記録する変数
    private var time:Long = 0L;



    // 誕生時のライフサイクルイベント
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)//画面レイアウトを設定

        //SurfaceHolderをView部品から取得
        val holder = surfaceView.holder

        //SurfaceHolderのコールバックに自クラスへの通知を追加
        holder.addCallback(this);
    }

    // 画面表示・再表示のライフサイクルイベント
    override fun onResume() {
        // 親クラスのonResume()処理
        super.onResume()
        // 自クラスのonResume()処理

    }

    // 画面が非表示の時のライフサイクルイベント
    override fun onPause() {
        super.onPause()

    }

    // 精度が変わった時のイベントコールバック
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    // センサーの値が変わった時のイベントコールバック
    override fun onSensorChanged(event: SensorEvent?) {
        //eventの中身がnullなら何もせずにreturn
        if(event == null){
            return;
        }

        //センサーが変わった時にボールを描画する情報を計算する
        //一番最初のセンサー検知の初期時間を取得
        if(time == 0L){
            //最初は現在のミリ秒システム時刻を設定
            time = System.currentTimeMillis()
        }

        //eventのセンサー種別が加速度センサーだったら以下を実行
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){

        }

        /*Log.d("TAG01","センサーが変わりました")
        // イベントが何もなかったらそのままリターン
        if(event == null){ return; }

        // センサーの値が変わったらログに出力
        // 加速度センサーか判定
        if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            // ログ出力用文字列を組み立て
            val str:String = "x = ${event.values[0].toString()}" +
                    ", y = ${event.values[1].toString()}" +
                    ", z = ${event.values[2].toString()}";
            // デバッグログに出力
            Log.d("加速度センサー", str);
        }*/



    }

    //Surfaceが生成された時のイベントに反応して呼ばれるコールバック
    //画面が表示状態になった時のイベント処理
    override fun surfaceCreated(holder: SurfaceHolder?) {
        // センサーマネージャをOSから取得
        val sensorManager =
            this.getSystemService(Context.SENSOR_SERVICE) as
                    SensorManager;
        // 加速度センサー(Accelerometer)を指定してセンサーマネージャからセンサーを取得
        val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // リスナー登録して加速度センサーの監視を開始
        sensorManager.registerListener(
            this,  // イベントリスナー機能をもつインスタンス（自クラスのインスタンス）
            accSensor, // 監視するセンサー（加速度センサー）
            SensorManager.SENSOR_DELAY_GAME // センサーの更新頻度
        )
    }

    //Surfaceが更新されたときにイベントに反応して呼ばれるコールバック
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        //Surfaceが変化するたびに幅と高さを設定
        this.surfaceWidth = width;
        this.surfaceHeight = height;
    }

    //Surfaceが破棄されたときにイベントに反応して呼ばれるコールバック
    //画面を非表示になった時のイベント処理
    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        // センサーマネージャを取得
        val sensorManager =
            this.getSystemService(Context.SENSOR_SERVICE) as
                    SensorManager;
        // センサーマネージャに登録したリスナーを解除（OFFにする）
        sensorManager.unregisterListener(this)
    }
}