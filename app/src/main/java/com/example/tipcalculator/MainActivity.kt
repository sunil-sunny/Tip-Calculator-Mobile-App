package com.example.tipcalculator

import android.annotation.SuppressLint
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /*Initialized all the android elements using lateinit*/

    lateinit var billAmountText: EditText
    lateinit var resultTip: TextView
    lateinit var resultFinalBill: TextView
    lateinit var buttonCalculate: Button
    lateinit var buttonReset: Button
    lateinit var partySize:Spinner
    lateinit var partyCost:TextView
    var partyCount="1" /*Setting the default value of party size to 1*/
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*setting up the default values of android elements*/
        tipSeekbar.progress=15
        percentage.text="15%"
        textTipAmount.text="0.0"
        textTotal.text="0.0"
        finalCostPerPerson.text="0.0"

        /*Setting up the initialzed variables with corresponding layout elements*/

        billAmountText=findViewById(R.id.textBillAmount) as EditText
        resultTip=findViewById(R.id.textTipAmount) as TextView
        resultFinalBill=findViewById(R.id.textTotal) as TextView
        buttonCalculate=findViewById(R.id.buttonCalcualte) as Button
        buttonReset=findViewById(R.id.buttonReset) as Button
        partySize= findViewById<Spinner>(R.id.spinnerPartySize)
        partyCost= findViewById<TextView>(R.id.finalCostPerPerson)



        /*Setting up on seek bar change listener to shows the run time change in corresponding text feild of tip percentage*/
        tipSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, seekvalue: Int, p2: Boolean) {
                percentage.text="$seekvalue%"
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        } )
        /*creating an array to link to the spinner element whihc takes PartySize*/
        val options= arrayOf("1","2","3","4","5","6","7","8","9","10")
        /*adding the created options to the spinner*/
        partySize.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)


        /*setting up the on item selected listner for spinner*/
        partySize.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
               partyCount=options.get(0)
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

               partyCount=options.get(p2)
            }
        }

        /*setting up the logic in button onclick listner for calcualte button*/
        buttonCalcualte.setOnClickListener {

            var decimalRegexPattern  = "^\\d+(?:\\.\\d{1,2})$".toRegex()
            if(billAmountText.text.toString().length==0){

                /*checking the bill amount length and throwing an error*/

                billAmountText!!.setError( "Enter valid bill amount")



            }
            else if(billAmountText.text.toString().contains('.') && !decimalRegexPattern.containsMatchIn(billAmountText.text)) {
                billAmountText.setError("Amount should have only two decimal points")

            }
            else if(billAmountText.text.toString().toDouble()<=0.0){

                /*checking if bill amount is 0 and throwing an error*/
                billAmountText!!.setError( "Bill Amount can not be zero")
            }
            else{
                /*Performing the calculation logic*/

                    var billAmount:Double=billAmountText.text.toString().toDouble();
                    val tipPercentage:Double=tipSeekbar.progress.toString().toDouble()
                    val tipAmount:Double=(billAmount)*((tipPercentage)/100)
                    //tipAmount=tipAmount
                    val a:String =tipAmount.toString()
                    resultTip.text="%.2f".format(a.toString().toDouble())
                    var finalBill=(billAmount+tipAmount)
                    val b:String=finalBill.toString()
                    resultFinalBill.text="%.2f".format(b.toString().toDouble())
                    var costOfEachPerson= (finalBill/partyCount.toDouble())
                    partyCost.text="%.2f".format("$costOfEachPerson".toDouble())
            }


        }

        /*Setting up the onclick listner for reset button*/
        buttonReset.setOnClickListener({

            /*logic for clearing all the data for reset button*/

            billAmountText.text=null
            resultTip.text="0.0"
            resultFinalBill.text="0.0"
            tipSeekbar.progress=15
            partyCost.text="0.0"
            partySize.setSelection(0)

        })
    }
}
