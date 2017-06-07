package com.vrgsoft.mygoal.presentation.common.view

import android.annotation.TargetApi
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout
import android.widget.TextView
import com.vrgsoft.mygoal.R

class CheckableButton : LinearLayout, Checkable {


    private var mContext: Context? = null

    // # Background Style Attributes
    private var checkedBackgroundColor = Color.BLACK
    private var uncheckedBackgroundColor = Color.BLACK
    /**
     * Right Now this is disabled

     * @param mFocusBackgroundColor
     */
    var focusBackgroundColor = Color.TRANSPARENT
        set(mFocusBackgroundColor) {
            field = mFocusBackgroundColor
            setupBackground()
        }
    private var mDisabledBackgroundColor = Color.parseColor("#f6f7f9")
    var disabledTextColor = Color.parseColor("#bec2c9")
    private var mDisabledBorderColor = Color.parseColor("#dddfe2")

    // # Text Style Attributes
    var checkedTextColor = Color.WHITE
    private var uncheckedTextColor = Color.WHITE
    private val mTextPosition = 1
    private var mDefaultTextSize = CheckableUtils.spToPx(context, 15f)
    var defaultTextGravity = 0x11 // Gravity.CENTER
    private lateinit var mText: String
    private val mDefaultTextFont = ""

    private var mBorderColor = Color.TRANSPARENT
    private var checkedBorderColor = Color.TRANSPARENT
    private var mBorderWidth = 0
    private var checkedBorderWidth = 0
    private  var mTextTypeFace: Typeface?=null
    private val disableClick: Boolean = false

    private var mRadius = 0

    private var mTextAllCaps = false
    private val isRippleEffect = false
    var textView: TextView? = null
    private var mListener: OnCheckedChangeListener? = null
    private var isChecked: Boolean = false

    constructor(context: Context) : super(context) {
        this.mContext = context
        mTextTypeFace = CheckableUtils.findFont(context, mDefaultTextFont, null)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        val attrsArray = context.obtainStyledAttributes(attrs, R.styleable.CheckableButton, 0, 0)
        initAttributesArray(attrs, attrsArray)
        attrsArray.recycle()
        init()
    }

    /**
     * Initialize CheckableButton View
     */
    private fun init() {

        //Init The container view: LinearLayout
        this.orientation = VERTICAL
        val containerParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        this.layoutParams = containerParams
        this.gravity = Gravity.CENTER
        this.isClickable = true
        this.isFocusable = true
        if (paddingLeft == 0 && paddingRight == 0 && paddingTop == 0 && paddingBottom == 0) {
            this.setPadding(10, 10, 10, 10)
        }

        //init the Textview:
        setUpTextView()
        setUpTextColorStates()

        setupBackground()

        // for (View view : views) {
        //this.addView(mTextView);
        //}
        setOnClickListener { toggle() }

        if (isChecked) {
            setChecked(true)
        }


    }

    private fun setUpTextView() {
        if (true) {
            textView = TextView(mContext)
            textView!!.text = mText
            textView!!.gravity = defaultTextGravity
            textView!!.setTextColor(if (isChecked()) checkedTextColor else uncheckedTextColor)
            textView!!.textSize = CheckableUtils.pxToSp(context, mDefaultTextSize.toFloat()).toFloat()

            textView!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            if (!isInEditMode && mTextTypeFace != null) {
                textView!!.typeface = mTextTypeFace
            }
        }
    }

    private fun setUpTextColorStates() {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled), // enabled
                intArrayOf(-android.R.attr.state_enabled), // disabled
                intArrayOf(-android.R.attr.state_checked), // unchecked
                intArrayOf(android.R.attr.state_checked), // checked
                intArrayOf(android.R.attr.state_pressed)  // pressed
        )

        val colors = intArrayOf(checkedTextColor, disabledTextColor, uncheckedTextColor, checkedTextColor, uncheckedTextColor)

        val myList = ColorStateList(states, colors)

        if (textView != null) {
            this.addView(textView)
        }
    }

    private fun initAttributesArray(attrs: AttributeSet, attrsArray: TypedArray) {

        checkedBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedColor, checkedBackgroundColor)
        uncheckedBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_unCheckColor, uncheckedBackgroundColor)
        mDisabledBackgroundColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledColor, mDisabledBackgroundColor)

        this.isEnabled = attrs.getAttributeBooleanValue("http://schemas.android.com/apk/res/android", "enabled", true)

        disabledTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledTextColor, disabledTextColor)
        mDisabledBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_disabledBorderColor, mDisabledBorderColor)
        checkedTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedTextColor, checkedTextColor)
        uncheckedTextColor = attrsArray.getColor(R.styleable.CheckableButton_cb_uncheckedTextColor, uncheckedTextColor)
        // if default color is set then the icon's color is the same (the default for icon's color)
        isChecked = attrsArray.getBoolean(R.styleable.CheckableButton_cb_isChecked, false)
        mDefaultTextSize = attrsArray.getDimension(R.styleable.CheckableButton_cb_textSize, mDefaultTextSize.toFloat()).toInt()
        defaultTextGravity = attrsArray.getInt(R.styleable.CheckableButton_cb_textGravity, defaultTextGravity)

        mBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_unCheckBorderColor, mBorderColor)
        checkedBorderColor = attrsArray.getColor(R.styleable.CheckableButton_cb_checkedBorderColor, checkedBorderColor)
        mBorderWidth = attrsArray.getDimension(R.styleable.CheckableButton_cb_borderWidth, mBorderWidth.toFloat()).toInt()
        checkedBorderWidth = attrsArray.getDimension(R.styleable.CheckableButton_cb_checkedborderWidth, checkedBorderWidth.toFloat()).toInt()

        mRadius = attrsArray.getDimension(R.styleable.CheckableButton_cb_radius, mRadius.toFloat()).toInt()

        mTextAllCaps = attrsArray.getBoolean(R.styleable.CheckableButton_cb_textAllCaps, false)

        val text = attrsArray.getString(R.styleable.CheckableButton_cb_text)
        if (text != null)
            mText = if (mTextAllCaps) text.toUpperCase() else text
    }

    /**
     * Uses the backgroud color of the unchecked state to create a lighter color
     * to be used for the focusable drawable

     * @param color
     * *
     * @return
     */
    private fun lightenColor(color: Int): Int {
        val hsv = FloatArray(3)
        val mColor: Int
        Color.colorToHSV(color, hsv)

        hsv[2] = 1.0f - 0.8f * (1.0f - hsv[2])
        mColor = Color.HSVToColor(hsv)
        return mColor
    }

    /**
     * SETup container's background
     * assign drawable states
     */
    private fun setupBackground() {

        // Default Drawable
        val defaultDrawable = GradientDrawable()
        defaultDrawable.cornerRadius = mRadius.toFloat()
        defaultDrawable.setColor(uncheckedBackgroundColor)

        //Focus Drawable
        lightenColor(uncheckedBackgroundColor)
        val focusDrawable = GradientDrawable()
        focusDrawable.cornerRadius = mRadius.toFloat()
        focusDrawable.setColor(focusBackgroundColor)

        // Disabled Drawable
        val disabledDrawable = GradientDrawable()
        disabledDrawable.cornerRadius = mRadius.toFloat()
        disabledDrawable.setColor(mDisabledBackgroundColor)
        disabledDrawable.setStroke(mBorderWidth, mDisabledBorderColor)

        // Disabled Drawable disabled
        val disabledDrawable2 = GradientDrawable()
        disabledDrawable2.cornerRadius = mRadius.toFloat()
        disabledDrawable2.setColor(mDisabledBackgroundColor)
        disabledDrawable2.setStroke(mBorderWidth, mDisabledBorderColor)

        // checked Drawable
        val drawable3 = GradientDrawable()
        drawable3.cornerRadius = mRadius.toFloat()
        drawable3.setColor(checkedBackgroundColor)


        // Handle Border
        if (mBorderColor != 0) {
            defaultDrawable.setStroke(mBorderWidth, mBorderColor)
            disabledDrawable.setStroke(mBorderWidth, mBorderColor)
        }
        if (checkedBorderColor != 0) {
            drawable3.setStroke(checkedBorderWidth, checkedBorderColor)
            disabledDrawable2.setStroke(mBorderWidth, checkedBorderColor)
        }

        // Handle disabled border color
        if (!isEnabled) {
            defaultDrawable.setStroke(mBorderWidth, mDisabledBorderColor)
        }


        if (isRippleEffect && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.background = getRippleDrawable(defaultDrawable, focusDrawable, disabledDrawable)

        } else {

            val states = StateListDrawable()
            // Focus/Pressed Drawable
            val drawable2 = GradientDrawable()
            drawable2.cornerRadius = mRadius.toFloat()
            drawable2.setColor(focusBackgroundColor)

            // Handle Button Border
            if (mBorderColor != 0) {
                drawable2.setStroke(mBorderWidth, mBorderColor)
            }

            if (!isEnabled) {
                drawable2.setStroke(mBorderWidth, mDisabledBorderColor)
            }

            if (focusBackgroundColor != 0) {
                states.addState(intArrayOf(android.R.attr.state_pressed), drawable2)
                states.addState(intArrayOf(android.R.attr.state_focused), drawable2)
                states.addState(intArrayOf(-android.R.attr.state_enabled), disabledDrawable)
                states.addState(intArrayOf(-android.R.attr.state_enabled, -android.R.attr.state_checked), disabledDrawable2)
            }
            if (checkedBackgroundColor != 0) {
                states.addState(intArrayOf(android.R.attr.state_checked), drawable3)
                states.addState(intArrayOf(-android.R.attr.state_checked), defaultDrawable)
            }
            states.addState(intArrayOf(), defaultDrawable)


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                this.setBackgroundDrawable(states)
            } else {
                this.background = states
            }

        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getRippleDrawable(defaultDrawable: Drawable, focusDrawable: Drawable, disabledDrawable: Drawable): Drawable {
        if (!isEnabled) {
            return disabledDrawable
        } else {
            return RippleDrawable(ColorStateList.valueOf(focusBackgroundColor), defaultDrawable, focusDrawable)
        }

    }

    override fun setChecked(checked: Boolean) {
        if (isChecked && !checked) {
            switchToHide()
            isChecked = false
        } else if (!isChecked && checked) {
            switchToCheck()
            isChecked = true
        }
        textView!!.setTextColor(if (checked) checkedTextColor else uncheckedTextColor)
        refreshDrawableState()
        if (mListener != null) {
            mListener!!.onCheckedChanged(this, checked)
        }
    }

    private fun switchToCheck() {
        //Might apply checked animations in the future
        //invalidate();
    }

    private fun switchToHide() {
        //Might apply checked animations in the future
        // invalidate();
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    public override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked()) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun toggle() {
        this.setChecked(!isChecked())
    }

    fun setOnCheckChangeLisnter(onCheckChangeListener: OnCheckedChangeListener) {
        mListener = onCheckChangeListener
    }

    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: View, isChecked: Boolean)
    }

    fun getCheckedBackgroundColor(): Int {
        return checkedBackgroundColor
    }

    fun setCheckedBackgroundColor(checkedBackgroundColor: Int) {
        this.checkedBackgroundColor = checkedBackgroundColor
        setupBackground()
    }

    fun getUncheckedBackgroundColor(): Int {
        return uncheckedBackgroundColor
    }

    fun setUncheckedBackgroundColor(uncheckedBackgroundColor: Int) {
        this.uncheckedBackgroundColor = uncheckedBackgroundColor
        setupBackground()
    }

    var disabledBackgroundColor: Int
        get() = mDisabledBackgroundColor
        set(mDisabledBackgroundColor) {
            this.mDisabledBackgroundColor = mDisabledBackgroundColor
            setupBackground()
        }

    var disabledBorderColor: Int
        get() = mDisabledBorderColor
        set(mDisabledBorderColor) {
            this.mDisabledBorderColor = mDisabledBorderColor
            setupBackground()
        }

    fun getUncheckedTextColor(): Int {
        return uncheckedTextColor
    }

    fun setUncheckedTextColor(uncheckedTextColor: Int) {
        this.uncheckedTextColor = uncheckedTextColor
        setupBackground()
    }

    var defaultTextSize: Int
        get() = mDefaultTextSize
        set(mDefaultTextSize) {
            this.mDefaultTextSize = CheckableUtils.spToPx(context, mDefaultTextSize.toFloat())
        }

    var text: String
        get() = mText
        set(text) = if (textView != null)
            this.textView!!.text = text
        else
            mText = text

    var borderColor: Int
        get() = mBorderColor
        set(mBorderColor) {
            this.mBorderColor = mBorderColor
            setupBackground()
        }

    fun getCheckedBorderColor(): Int {
        return checkedBorderColor
    }

    fun setCheckedBorderColor(checkedBorderColor: Int) {
        this.checkedBorderColor = checkedBorderColor
        setupBackground()   
    }

    var borderWidth: Int
        get() = mBorderWidth
        set(mBorderWidth) {
            this.mBorderWidth = mBorderWidth
            setupBackground()
        }

    fun getCheckedBorderWidth(): Int {
        return checkedBorderWidth
    }

    fun setCheckedBorderWidth(checkedBorderWidth: Int) {
        this.checkedBorderWidth = checkedBorderWidth
        setupBackground()
    }

    var radius: Int
        get() = mRadius
        set(mRadius) {
            this.mRadius = mRadius
            setupBackground()
        }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}