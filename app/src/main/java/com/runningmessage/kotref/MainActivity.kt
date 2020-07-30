package com.runningmessage.kotref

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import com.runningmessage.kotref.java.discuss.Discuss001
import com.runningmessage.kotref.kotlin.`class`.ClassAndInheritance
import com.runningmessage.kotref.kotlin.basic.ReturnsAndJumps
import com.runningmessage.kotref.kotlin.coroutines.*
import com.runningmessage.kotref.kotlin.discuss.Discuss
import com.runningmessage.kotref.kotlin.discuss.Discuss003
import com.runningmessage.kotref.kotlin.discuss.Discuss004
import com.runningmessage.kotref.kotlin.overview.Feature0101
import com.runningmessage.kotref.kotlin.overview.Feature0102
import com.runningmessage.kotref.kotlin.overview.Feature0103
import com.runningmessage.kotref.kotlin.overview.Multiplatform
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import kotlin.contracts.ExperimentalContracts

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@ExperimentalContracts
class MainActivity : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

/*        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

    }

//    fun testButton(){
//        container.button(""){
//            onClick {
//
//            }
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @ExperimentalContracts
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            return PlaceholderFragment.mapList.size
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {


        @ExperimentalCoroutinesApi
        @ObsoleteCoroutinesApi
        @ExperimentalContracts
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            val index = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
            rootView.section_label.text = getString(R.string.section_format, index)


            if (mapList.size >= index && index > 0) {
                for ((name, apply) in mapList[index - 1]) {
                    if (rootView.text_container is ViewGroup) rootView.text_container.addButton(
                        name,
                        apply
                    )
                }
            }
            return rootView
        }

        private fun ViewGroup.addButton(text: String, click: () -> Any) {
            val btn = TextView(context)
            btn.gravity = Gravity.CENTER
            btn.setBackgroundResource(R.drawable.kt_button_bg)
            btn.text = text
            btn.minHeight = 90
            if (this is LinearLayout) {
                val lp = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                lp.gravity = Gravity.CENTER_HORIZONTAL
                lp.bottomMargin = 20
                btn.layoutParams = lp
            }
            btn.setOnClickListener {
                /** Using kotlinx coroutines*/
//                GlobalScope.launch {
//
//                    val result = click.invoke().toString()
//                    val resultText = "$text = $result"
//                    activity?.runOnUiThread { btn.text = resultText }
//                }
                //click.invoke() TODO m:lorss this line to check whether function will crash or block thread
                /** Using anko*/
                doAsync {
                    val result = click.invoke().toString()
                    val resultText = "$text = $result"
                    uiThread { btn.text = resultText }
                }


            }
            addView(btn)
        }


        companion object {

            @ExperimentalCoroutinesApi
            @ObsoleteCoroutinesApi
            @ExperimentalContracts
            val mapList: ArrayList<LinkedHashMap<String, () -> Any>>
                get() {

                    /**OverView*/
                    val map00 = LinkedHashMap<String, () -> Any>()
                    map00["Multiplatform.Companion::tString"] =
                            Multiplatform.Companion::tString
                    map00["Multiplatform.Companion::tConstructor"] =
                            Multiplatform.Companion::tConstructor
                    map00["Feature0101.Companion::testGroupingBy"] =
                            Feature0101.Companion::testGroupingBy

                    map00["Feature0102.Companion::t01"] = Feature0102.Companion::t01
                    map00["Feature0102.Companion::t02"] = Feature0102.Companion::t02

                    map00["Feature0103.Companion::t01"] = Feature0103.Companion::t01
                    map00["Feature0103.Companion::t02"] = Feature0103.Companion::t02
                    map00["Feature0103.Companion::t03"] = Feature0103.Companion::t03
                    map00["Feature0103.Companion::t04"] = Feature0103.Companion::t04
                    map00["Feature0103.Companion::t05"] = Feature0103.Companion::t05
                    map00["Feature0103.Companion::t06"] = Feature0103.Companion::t06

                    map00["Discuss.Companion::t01"] = Discuss.Companion::t01
                    map00["Discuss.Companion::t02"] = Discuss.Companion::t02
                    map00["Discuss003.Companion::t01"] = Discuss003.Companion::t01
                    map00["Discuss003.Companion::t02"] = Discuss003.Companion::t02
                    map00["Discuss003.Companion::t04"] = Discuss003.Companion::t04
                    map00["Discuss001.test"] = Discuss001::test
                    map00["Discuss004.Companion::testContext1"] = Discuss004.Companion::testContext1
                    map00["Discuss004.Companion::testContext2"] = Discuss004.Companion::testContext2
                    map00["Discuss004.Companion::testContext3"] = Discuss004.Companion::testContext3

                    /**Basic*/
                    val map01 = LinkedHashMap<String, () -> Any>()
                    map01["ReturnsAndJumps.Companion::t01"] = ReturnsAndJumps.Companion::t01
//                    map01["ReturnsAndJumps.Companion::t02"] = ReturnsAndJumps.Companion::t02
//                    map01["ReturnsAndJumps.Companion::t03"] = ReturnsAndJumps.Companion::t03
//                    map01["ReturnsAndJumps.Companion::t04"] = ReturnsAndJumps.Companion::t04
//                    map01["ReturnsAndJumps.Companion::t05"] = ReturnsAndJumps.Companion::t05
//                    map01["ReturnsAndJumps.Companion::t06"] = ReturnsAndJumps.Companion::t06

                    /**Classes and Objects*/
                    val map02 = LinkedHashMap<String, () -> Any>()
                    map02["ClassAndInheritance.Companion::t01"] =
                            ClassAndInheritance.Companion::t01
//                    map02["ClassAndInheritance.Companion::t02"] = ClassAndInheritance.Companion::t02
//                    map02["ClassAndInheritance.Companion::t03"] = ClassAndInheritance.Companion::t03
//                    map02["ClassAndInheritance.Companion::t04"] = ClassAndInheritance.Companion::t04
//                    map02["ClassAndInheritance.Companion::t05"] = ClassAndInheritance.Companion::t05
//                    map02["ClassAndInheritance.Companion::t06"] = ClassAndInheritance.Companion::t06

                    /**Coroutines*/
                    val map10 = LinkedHashMap<String, () -> Any>()

                    map10["Basic.Companion::t01"] = Basic.Companion::t01
                    map10["Basic.Companion::t02"] = Basic.Companion::t02
                    map10["Basic.Companion::t03"] = Basic.Companion::t03
                    map10["Basic.Companion::t04"] = Basic.Companion::t04
                    map10["Basic.Companion::t05"] = Basic.Companion::t05
                    map10["Basic.Companion::t06"] = Basic.Companion::t06

                    map10["CancelTimeout.Companion::t01"] = CancelTimeout.Companion::t01
                    map10["CancelTimeout.Companion::t02"] = CancelTimeout.Companion::t02
                    map10["CancelTimeout.Companion::t03"] = CancelTimeout.Companion::t03
                    map10["CancelTimeout.Companion::t04"] = CancelTimeout.Companion::t04
                    map10["CancelTimeout.Companion::t05"] = CancelTimeout.Companion::t05

                    map10["Channels.Companion.t01"] = Channels.Companion::t01
                    map10["Channels.Companion.t02"] = Channels.Companion::t02
                    map10["Channels.Companion.t03"] = Channels.Companion::t03
                    map10["Channels.Companion.t04"] = Channels.Companion::t04
                    map10["Channels.Companion.t05"] = Channels.Companion::t05
                    map10["Channels.Companion.t06"] = Channels.Companion::t06
                    map10["Channels.Companion.t07"] = Channels.Companion::t07
                    map10["Channels.Companion.t08"] = Channels.Companion::t08
                    map10["Channels.Companion.t09"] = Channels.Companion::t09
                    map10["Channels.Companion.t10"] = Channels.Companion::t10

                    map10["ComSusFun.Companion.t01"] = ComSusFun.Companion::t01
                    map10["ComSusFun.Companion.t02"] = ComSusFun.Companion::t02
                    map10["ComSusFun.Companion.t03"] = ComSusFun.Companion::t03
                    map10["ComSusFun.Companion.t04"] = ComSusFun.Companion::t04
                    map10["ComSusFun.Companion.t05"] = ComSusFun.Companion::t05
                    map10["ComSusFun.Companion.t05"] = ComSusFun.Companion::t05
                    map10["ComSusFun.Companion.t051"] = ComSusFun.Companion::t051

                    map10["ContextDispatchers.Companion.t01"] = ContextDispatchers.Companion::t01
                    map10["ContextDispatchers.Companion.t02"] = ContextDispatchers.Companion::t02
                    map10["ContextDispatchers.Companion.t03"] = ContextDispatchers.Companion::t03
                    map10["ContextDispatchers.Companion.t04"] = ContextDispatchers.Companion::t04
                    map10["ContextDispatchers.Companion.t05"] = ContextDispatchers.Companion::t05
                    map10["ContextDispatchers.Companion.t06"] = ContextDispatchers.Companion::t06
                    map10["ContextDispatchers.Companion.t07"] = ContextDispatchers.Companion::t07
                    map10["ContextDispatchers.Companion.t08"] = ContextDispatchers.Companion::t08
                    map10["ContextDispatchers.Companion.t09"] = ContextDispatchers.Companion::t09
                    map10["ContextDispatchers.Companion.t10"] = ContextDispatchers.Companion::t10
                    map10["ContextDispatchers.Companion.t11"] = ContextDispatchers.Companion::t11

                    map10["ExceptionHandling.Companion.t01"] = ExceptionHandling.Companion::t01
                    map10["ExceptionHandling.Companion.t02"] = ExceptionHandling.Companion::t02
                    map10["ExceptionHandling.Companion.t03"] = ExceptionHandling.Companion::t03
                    map10["ExceptionHandling.Companion.t031"] = ExceptionHandling.Companion::t031
                    map10["ExceptionHandling.Companion.t04"] = ExceptionHandling.Companion::t04
                    map10["ExceptionHandling.Companion.t041"] = ExceptionHandling.Companion::t041
                    map10["ExceptionHandling.Companion.t05"] = ExceptionHandling.Companion::t05
                    map10["ExceptionHandling.Companion.t06"] = ExceptionHandling.Companion::t06
                    map10["ExceptionHandling.Companion.t07"] = ExceptionHandling.Companion::t07

                    map10["Select.Companion.t01"] = Select.Companion::t01
                    map10["Select.Companion.t02"] = Select.Companion::t02
                    map10["Select.Companion.t03"] = Select.Companion::t03
                    map10["Select.Companion.t04"] = Select.Companion::t04
                    map10["Select.Companion.t05"] = Select.Companion::t05

                    map10["SMSConcurrency.Companion.t01"] = SMSConcurrency.Companion::t01
                    map10["SMSConcurrency.Companion.t02"] = SMSConcurrency.Companion::t02
                    map10["SMSConcurrency.Companion.t03"] = SMSConcurrency.Companion::t03
                    map10["SMSConcurrency.Companion.t04"] = SMSConcurrency.Companion::t04
                    map10["SMSConcurrency.Companion.t05"] = SMSConcurrency.Companion::t05
                    map10["SMSConcurrency.Companion.t06"] = SMSConcurrency.Companion::t06
//                    map1["SMSConcurrency.Companion.t07"] = SMSConcurrency.Companion::t07
//                    map1["SMSConcurrency.Companion.t08"] = SMSConcurrency.Companion::t08
//                    map1["SMSConcurrency.Companion.t09"] = SMSConcurrency.Companion::t09
//                    map1["SMSConcurrency.Companion.t10"] = SMSConcurrency.Companion::t10
//                    map1["SMSConcurrency.Companion.t11"] = SMSConcurrency.Companion::t11
//                    map1["SMSConcurrency.Companion.t12"] = SMSConcurrency.Companion::t12
//                    map1["SMSConcurrency.Companion.t13"] = SMSConcurrency.Companion::t13
//                    map1["SMSConcurrency.Companion.t14"] = SMSConcurrency.Companion::t14
//                    map1["SMSConcurrency.Companion.t15"] = SMSConcurrency.Companion::t15
//                    map1["SMSConcurrency.Companion.t16"] = SMSConcurrency.Companion::t16
//                    map1["SMSConcurrency.Companion.t17"] = SMSConcurrency.Companion::t17
//                    map1["SMSConcurrency.Companion.t18"] = SMSConcurrency.Companion::t18
//                    map1["SMSConcurrency.Companion.t19"] = SMSConcurrency.Companion::t19

                    val list = ArrayList<LinkedHashMap<String, () -> Any>>()
                    list.add(map00)
                    list.add(map01)
                    list.add(map02)
                    list.add(map10)
                    return list
                }

            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private const val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
