package com.daveloper.rickandmortyapp.core.ui.vectors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object AppIcon {
    @Composable
    fun filterAlt(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "filter_alt",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(23.208f, 21.625f)
                    verticalLineToRelative(9.833f)
                    quadToRelative(0f, 0.709f, -0.479f, 1.167f)
                    quadToRelative(-0.479f, 0.458f, -1.187f, 0.458f)
                    horizontalLineToRelative(-3.084f)
                    quadToRelative(-0.708f, 0f, -1.187f, -0.458f)
                    quadToRelative(-0.479f, -0.458f, -0.479f, -1.167f)
                    verticalLineToRelative(-9.833f)
                    lineTo(7.125f, 9.25f)
                    quadToRelative(-0.583f, -0.708f, -0.167f, -1.521f)
                    quadToRelative(0.417f, -0.812f, 1.334f, -0.812f)
                    horizontalLineToRelative(23.416f)
                    quadToRelative(0.917f, 0f, 1.334f, 0.812f)
                    quadToRelative(0.416f, 0.813f, -0.167f, 1.521f)
                    close()
                    moveTo(20f, 21.375f)
                    lineToRelative(9.333f, -11.833f)
                    horizontalLineTo(10.667f)
                    close()
                    moveToRelative(0f, 0f)
                    close()
                }
            }.build()
        }
    }

    @Composable
    fun arrowUpward(): ImageVector {
        return remember {
            ImageVector.Builder(
                name = "arrow_upward",
                defaultWidth = 40.0.dp,
                defaultHeight = 40.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(20f, 33.125f)
                    quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                    reflectiveQuadToRelative(-0.375f, -0.958f)
                    verticalLineTo(11.917f)
                    lineToRelative(-9.041f, 9f)
                    quadToRelative(-0.417f, 0.416f, -0.938f, 0.416f)
                    quadToRelative(-0.521f, 0f, -0.937f, -0.416f)
                    quadToRelative(-0.375f, -0.375f, -0.375f, -0.917f)
                    reflectiveQuadToRelative(0.375f, -0.917f)
                    lineTo(19.083f, 7.792f)
                    quadToRelative(0.209f, -0.209f, 0.438f, -0.292f)
                    quadToRelative(0.229f, -0.083f, 0.479f, -0.083f)
                    quadToRelative(0.25f, 0f, 0.479f, 0.083f)
                    quadToRelative(0.229f, 0.083f, 0.438f, 0.292f)
                    lineToRelative(11.291f, 11.291f)
                    quadToRelative(0.375f, 0.375f, 0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.375f, 0.917f)
                    quadToRelative(-0.416f, 0.416f, -0.937f, 0.416f)
                    quadToRelative(-0.521f, 0f, -0.938f, -0.416f)
                    lineToRelative(-9f, -9f)
                    verticalLineToRelative(19.875f)
                    quadToRelative(0f, 0.583f, -0.395f, 0.958f)
                    quadToRelative(-0.396f, 0.375f, -0.938f, 0.375f)
                    close()
                }
            }.build()
        }
    }
}