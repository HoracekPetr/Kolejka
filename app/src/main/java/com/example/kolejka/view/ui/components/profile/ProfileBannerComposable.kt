package com.example.kolejka.view.ui.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import coil.compose.rememberImagePainter
import com.example.kolejka.R
import com.example.kolejka.models.User
import com.example.kolejka.view.theme.*
import com.example.kolejka.view.util.Constants.SUM_THRESHOLD
import kotlin.math.abs


@Composable
fun ProfileBannerComposable(
    modifier: Modifier = Modifier,
    user: User,
    isLoggedUser: Boolean,
    onEditIconClick: () -> Unit = {},
    onLogoutIconClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(PaddingMedium),
        shape = RoundedCornerShape(Space12),
        border = BorderStroke(2.dp, BlackAccent),
        backgroundColor = Color(user.bannerR / 255, user.bannerG / 255, user.bannerB / 255)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingSmall),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(ProfilePicSize)
                    .shadow(
                        elevation = Space12,
                        shape = CircleShape,
                        clip = true
                    ),
                painter = rememberImagePainter(data = user.profilePictureUrl, builder = {
                    crossfade(true)
                }),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(Space4))
            Text(
                text = user.username,
                style = Typography.body1,
                color = bannerTextColor(red = user.bannerR, green = user.bannerG, blue = user.bannerB)
            )
            Spacer(modifier = Modifier.size(Space8))
            if (isLoggedUser) {
                Row {
                    Icon(
                        modifier = Modifier.clickable { onEditIconClick() },
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = bannerTextColor(red = user.bannerR, green = user.bannerG, blue = user.bannerB)
                    )
                    Spacer(modifier = Modifier.size(Space36))
                    Icon(
                        modifier = Modifier.clickable { onLogoutIconClick() },
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Logout",
                        tint = bannerTextColor(red = user.bannerR, green = user.bannerG, blue = user.bannerB)
                    )
                }
            }

        }
    }
}

fun bannerTextColor(red: Float, green: Float, blue: Float): Color{

    if(red == 255F || green == 255F){
        return Color.Black
    }

    if(red < 122F || green < 122F){
        return Color.White
    }

    return Color.Black

}

