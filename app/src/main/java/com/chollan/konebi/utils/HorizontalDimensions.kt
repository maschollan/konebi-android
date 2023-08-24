package com.chollan.konebi.utils


interface HorizontalDimensions {
    val xSpacing: Float
    val scalableStartPadding: Float
    val scalableEndPadding: Float
    val unscalableStartPadding: Float
    val unscalableEndPadding: Float
    val startPadding: Float
        get() = scalableStartPadding + unscalableStartPadding

    val endPadding: Float
        get() = scalableEndPadding + unscalableEndPadding

    val padding: Float
        get() = startPadding + endPadding

    fun getContentWidth(maxMajorEntryCount: Int): Float = xSpacing * (maxMajorEntryCount - 1) + padding

    fun scaled(scale: Float): HorizontalDimensions = HorizontalDimensions(
        xSpacing * scale,
        scalableStartPadding * scale,
        scalableEndPadding * scale,
        unscalableStartPadding,
        unscalableEndPadding,
    )
}

fun HorizontalDimensions(
    xSpacing: Float,
    scalableStartPadding: Float,
    scalableEndPadding: Float,
    unscalableStartPadding: Float,
    unscalableEndPadding: Float,
): HorizontalDimensions = object : HorizontalDimensions {
    override val xSpacing: Float = xSpacing
    override val scalableStartPadding: Float = scalableStartPadding
    override val scalableEndPadding: Float = scalableEndPadding
    override val unscalableStartPadding: Float = unscalableStartPadding
    override val unscalableEndPadding: Float = unscalableEndPadding
}