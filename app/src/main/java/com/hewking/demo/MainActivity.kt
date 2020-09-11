package com.hewking.demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hewking.demo.androidview.cardview.CardViewFragment
import com.hewking.demo.androidview.constrainlayout.ConstrainDemoFragment
import com.hewking.demo.androidview.dialog.DialogProgressFragment
import com.hewking.demo.androidview.flexboxlayout.FlexBoxLayoutFragment
import com.hewking.demo.res.DemoActivity
import com.hewking.custom.R
import com.hewking.demo.animation.ViewLayoutAnimFragment
import com.hewking.custom.databinding.ActivityNaviBinding
import com.hewking.demo.androidview.image.ImageDemoFragment
import com.hewking.demo.dispatch.DispatchFragment
import com.hewking.utils.L
import com.hewking.utils.NotchCompat
import com.hewking.utils.v
import com.hewking.demo.language.LangageSwitchFragment
import com.hewking.demo.language.LanguageActivity
import com.hewking.demo.third.ImageExFragment


class MainActivity : LanguageActivity() {

    private val binding by lazy { ActivityNaviBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initView()
//        debugParams()

    }

    private fun debugParams() {
        L.d("notch" + NotchCompat.hasNotchInScreen(this).toString())
    }

    private fun initView() {
        val refreshLayout = binding.refreshlayout
        val recyclerView = binding.recyclerview

        refreshLayout.setOnRefreshListener {

        }

        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.addItemDecoration(RBaseItemDecoration())
        recyclerView.adapter = mAdapter
    }

    fun createItems() : MutableList<Item>{
        val list = mutableListOf<Item>()
        list.add(Item(0,getString(R.string.text_home),DemoListFragment::class.java))
        list.add(Item(1,getString(R.string.text_htextview),HTextViewFragment::class.java))
        list.add(Item(2,getString(R.string.text_stickytop), StickTopFragment::class.java))
        list.add(Item(3,getString(R.string.text_language_switch),LangageSwitchFragment::class.java))
        list.add(Item(4,"拉钩主页", LagouTopFragment::class.java))
        list.add(Item(5,"自定义view Demo", CustomViewFragment::class.java))
        list.add(Item(6,"EditText Demo",EditTextTestFragment::class.java))
        list.add(Item(7, "shadow 阴影 Demo", CardViewFragment::class.java))
        list.add(Item(8,"ProgressDialog Demo",DialogProgressFragment::class.java))
        list.add(Item(9,"GestureDetector Demo", GestureDetectorDemoFragment::class.java))
        list.add(Item(10,"ImageExDemo Demo",ImageExFragment::class.java))
        list.add(Item(11,"DemoActivity Demo", DemoActivity::class.java,2))
        list.add(Item(12,"DispatchFragment Demo", DispatchFragment::class.java))
        list.add(Item(13,"RecyclerTestFragent Demo",LoadRecyclerFragment::class.java))
        list.add(Item(13,"WebView Demo",WebViewFragment::class.java))
        list.add(Item(14,"ViewDragHelper Demo",ViewDragFragment::class.java))
        list.add(Item(15,"TinderStacklayout Demo",TinderStackLayoutFragment::class.java))
        list.add(Item(17,"TideRappleView Demo",TideRappleFragment::class.java))
        list.add(Item(18, "FlexBoxLayout 自定义 FlowLayout Demo", FlexBoxLayoutFragment::class.java))
        list.add(Item(19, "ImageView ScaleType Demo", ImageScaleTypeFragment::class.java))
        list.add(Item(20, "XfermodeSampleView Demo", XfermodeFragment::class.java))
        list.add(Item(21, "ConstrainLayout Demo", ConstrainDemoFragment::class.java))
        list.add(Item(22, "CustomDrawable Demo", CustomDrawableFragment::class.java))
        list.add(Item(23, "MultiTypeRecyclerView Demo", MultiTypeRecyclerFragment::class.java))
        list.add(Item(24, "WaterRippleCiew Demo", WaterRippleFragment::class.java))
        list.add(Item(25, "GalleryLayout Demo", GalleryLayoutFragment::class.java))
        list.add(Item(26, "FlowLayoutManager Demo", FlowLayoutManagerFragment::class.java))
        list.add(Item(27, "HighLightChildFrameLayout Demo", GuideLayoutDemoFragment::class.java))
        list.add(Item(28,"ViewLayoutanimFragment Demo",ViewLayoutAnimFragment::class.java))
        list.add(Item(29,"PointerPanelFragment Demo",PointerPanelFragment::class.java))
        list.add(Item(30,"ImageDemoFragment",ImageDemoFragment::class.java))
        return list
    }

    val mAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> by lazy {
        object : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

            val datas = createItems()

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_text,parent,false)
                return object : RecyclerView.ViewHolder(itemView){
                }
            }

            override fun getItemCount(): Int {
                return datas.size
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val itemView = holder.itemView
                itemView.v<TextView>(R.id.tv_text)?.text = datas[position].info
                itemView.setOnClickListener {
                    if (datas[position].type == 1) {
                        val intent = Intent(this@MainActivity, DemoFragmentActivity::class.java)
                        intent.putExtra(DemoFragmentActivity.FRAGMENT, datas[position].clazz.name)
                        this@MainActivity.startActivity(intent)
                    } else {
                        val intent = Intent(this@MainActivity,datas[position].clazz)
                        intent.resolveActivity(this@MainActivity.packageManager)
                        this@MainActivity.startActivity(intent)
                    }
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        L.d("onTouchEvent","Dispatch MainActivity")
        return super.onTouchEvent(event)
    }

    data class Item(val id : Int,val info : String ,val clazz: Class<*>,val type : Int = 1)

    companion object {
        val NESTED_SCROLL = 0x0001
    }


}
