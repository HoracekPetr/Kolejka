package com.example.kolejka.view.theme

import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.kolejka.R

val roboto_mono = FontFamily(
        Font(R.font.robotomono_bold, FontWeight.Bold),
        Font(R.font.robotomono_light, FontWeight.Light),
        Font(R.font.robotomono_medium, FontWeight.Medium),
        Font(R.font.robotomono_regular, FontWeight.Normal),
        Font(R.font.robotomono_semibold, FontWeight.SemiBold),
        Font(R.font.robotomono_thin, FontWeight.Thin),
)

val poppins = FontFamily(
        Font(R.font.poppins_bold, FontWeight.Bold),
        Font(R.font.poppins_light, FontWeight.Light),
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_thin, FontWeight.Thin),
)



// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp
        ),
        body2 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        //Nadpisy - medium
        h1 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
        ),
        //Nadpisy - regular
        h2 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp
        ),
        h3 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
        ),
        //Dodatky
        subtitle1 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        subtitle2 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
        ),
        caption = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
        ),
        h4 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
        ),
        h5 = TextStyle(
                fontFamily = roboto_mono,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        )
)

val Typography_Poppins = Typography(
        body1 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp
        ),
        body2 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        //Nadpisy - medium
        h1 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
        ),
        //Nadpisy - regular
        h2 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp
        ),
        h3 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
        ),
        //Dodatky
        subtitle1 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        ),
        subtitle2 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
        ),
        caption = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
        ),
        h4 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp
        ),
        h5 = TextStyle(
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
        )
)