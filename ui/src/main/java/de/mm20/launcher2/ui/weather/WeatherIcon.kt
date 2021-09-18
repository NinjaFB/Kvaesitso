package de.mm20.launcher2.ui.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.mm20.launcher2.ui.*
import de.mm20.launcher2.ui.icons.*

@Composable
fun WeatherIcon(
    icon: WeatherIcon,
    night: Boolean = false
) {
    Box(
        modifier = Modifier
            .size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        SunMoon(icon, night)
        Precipitation(icon)
        LightningBolts(icon)
        Cloud1(icon)
        Cloud2(icon)
        Cloud3(icon)
        HotCold(icon)
        Wind(icon)
        Fog(icon)
    }
}

@Composable
private fun SunMoon(icon: WeatherIcon, night: Boolean) {
    when (icon) {
        WeatherIcon.Clear,
        WeatherIcon.PartlyCloudy,
        WeatherIcon.BrokenClouds,
        WeatherIcon.Haze,
        WeatherIcon.MostlyCloudy -> {
        }
        else -> return
    }

    val color = if (night) MaterialTheme.colors.weatherMoon else MaterialTheme.colors.weatherSun

    val scale = when (icon) {
        WeatherIcon.Clear,
        WeatherIcon.PartlyCloudy,
        WeatherIcon.BrokenClouds,
        WeatherIcon.Haze -> 1f
        WeatherIcon.MostlyCloudy -> 0.8f
        else -> 0f
    }

    val offset = when (icon) {
        WeatherIcon.MostlyCloudy -> DpOffset(2.dp, -3.dp)
        WeatherIcon.PartlyCloudy -> DpOffset(2.dp, -2.dp)
        else -> DpOffset.Zero
    }

    Icon(
        if (night) Icons.Rounded.DarkMode else Icons.Rounded.LightMode,
        null,
        modifier = Modifier
            .size(24.dp)
            .offset(offset.x, offset.y)
            .scale(scale),
        tint = color
    )
}

@Composable
private fun Cloud1(icon: WeatherIcon) {

    when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain,
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog,
        WeatherIcon.MostlyCloudy,
        WeatherIcon.PartlyCloudy,
        WeatherIcon.BrokenClouds -> {
        }
        else -> return
    }

    val scale = when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain,
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> 1.4f
        WeatherIcon.MostlyCloudy,
        WeatherIcon.PartlyCloudy -> 1f
        WeatherIcon.BrokenClouds -> 0.9f
        else -> 0f
    }
    val offset = when (icon) {
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> DpOffset(0.dp, -7.dp)
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> DpOffset(0.dp, -2.5.dp)
        WeatherIcon.MostlyCloudy -> DpOffset(-2.5.dp, 0.dp)
        WeatherIcon.PartlyCloudy -> DpOffset(-1.5.dp, 2.dp)
        WeatherIcon.BrokenClouds -> DpOffset(-2.5.dp, 3.5.dp)
        else -> DpOffset.Zero
    }
    val color = when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> MaterialTheme.colors.weatherCloudDark2
        WeatherIcon.Showers,
        WeatherIcon.Sleet,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> MaterialTheme.colors.weatherCloudDark1
        WeatherIcon.Drizzle,
        WeatherIcon.Snow -> MaterialTheme.colors.weatherCloudMedium2
        WeatherIcon.MostlyCloudy -> MaterialTheme.colors.weatherCloudLight2
        WeatherIcon.PartlyCloudy,
        WeatherIcon.BrokenClouds -> MaterialTheme.colors.weatherCloudLight1
        else -> MaterialTheme.colors.weatherCloudMedium2
    }

    Icon(
        Icons.Rounded.WeatherCloud,
        null,
        modifier = Modifier
            .size(20.dp)
            .offset(offset.x, offset.y)
            .scale(scale),
        tint = color
    )
}

@Composable
private fun Cloud2(icon: WeatherIcon) {

    when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain,
        WeatherIcon.MostlyCloudy,
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog,
        WeatherIcon.BrokenClouds -> {
        }
        else -> return
    }

    val scale = when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain,
        WeatherIcon.MostlyCloudy,
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> 1.1f
        WeatherIcon.BrokenClouds -> 0.7f
        else -> 0f
    }
    val offset = when (icon) {
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> DpOffset(-3.dp, -3.dp)
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> DpOffset(-3.dp, 1.5.dp)
        WeatherIcon.BrokenClouds -> DpOffset(4.5.dp, -4.5.dp)
        WeatherIcon.MostlyCloudy -> DpOffset(2.dp, 2.5.dp)
        else -> DpOffset.Zero
    }
    val color = when (icon) {
        WeatherIcon.BrokenClouds -> MaterialTheme.colors.weatherCloudLight2
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> MaterialTheme.colors.weatherCloudMedium2
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> MaterialTheme.colors.weatherCloudMedium1
        WeatherIcon.MostlyCloudy -> MaterialTheme.colors.weatherCloudMedium1
        else -> MaterialTheme.colors.weatherCloudMedium2
    }

    Icon(
        Icons.Rounded.WeatherCloud,
        null,
        modifier = Modifier
            .size(20.dp)
            .offset(offset.x, offset.y)
            .scale(scale),
        tint = color
    )
}

@Composable
private fun Cloud3(icon: WeatherIcon) {
    when (icon) {
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain,
        WeatherIcon.Cloudy -> {

        }
        else -> return
    }
    val offset = when (icon) {
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Sleet,
        WeatherIcon.Snow,
        WeatherIcon.Hail,
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> DpOffset(3.dp, -2.dp)
        WeatherIcon.Cloudy,
        WeatherIcon.Fog -> DpOffset(3.dp, 2.5.dp)
        else -> DpOffset.Zero
    }
    val color = when (icon) {
        WeatherIcon.Thunderstorm,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstorm,
        WeatherIcon.HeavyThunderstormWithRain -> MaterialTheme.colors.weatherCloudDark1
        WeatherIcon.Showers,
        WeatherIcon.Sleet,
        WeatherIcon.Hail,
        WeatherIcon.Cloudy -> MaterialTheme.colors.weatherCloudMedium2
        WeatherIcon.Drizzle,
        WeatherIcon.Snow -> MaterialTheme.colors.weatherCloudLight2
        else -> MaterialTheme.colors.weatherCloudMedium2
    }

    Icon(
        Icons.Rounded.WeatherCloud,
        null,
        modifier = Modifier
            .size(32.dp)
            .offset(offset.x, offset.y),
        tint = color
    )
}

@Composable
private fun Precipitation(icon: WeatherIcon) {
    if (icon == WeatherIcon.Sleet) {
        Icon(
            modifier = Modifier
                .offset(y = 2.dp)
                .size(32.dp),
            imageVector = Icons.Rounded.WeatherSleetSnow,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherSnow
        )
        Icon(
            modifier = Modifier
                .offset(y = 2.dp)
                .size(32.dp),
            imageVector = Icons.Rounded.WeatherSleetRain,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherRain
        )
        return
    }

    when (icon) {
        WeatherIcon.Showers,
        WeatherIcon.Drizzle,
        WeatherIcon.Hail,
        WeatherIcon.Snow,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstormWithRain -> {
        }
        else -> return
    }
    val vector = when (icon) {
        WeatherIcon.Drizzle -> Icons.Rounded.WeatherLightRain
        WeatherIcon.Showers,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstormWithRain -> Icons.Rounded.WeatherRain
        WeatherIcon.Hail, WeatherIcon.Snow -> Icons.Rounded.WeatherHail
        else -> Icons.Rounded.WeatherLightRain
    }
    val color = when (icon) {
        WeatherIcon.Drizzle,
        WeatherIcon.Showers,
        WeatherIcon.ThunderstormWithRain,
        WeatherIcon.HeavyThunderstormWithRain -> MaterialTheme.colors.weatherRain
        WeatherIcon.Hail -> MaterialTheme.colors.weatherHail
        WeatherIcon.Snow -> MaterialTheme.colors.weatherSnow
        else -> Color.Unspecified
    }
    Icon(
        modifier = Modifier
            .offset(y = 2.dp)
            .size(32.dp),
        imageVector = vector,
        contentDescription = null,
        tint = color
    )
}

@Composable
private fun HotCold(icon: WeatherIcon) {
    if (icon == WeatherIcon.Hot) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = Icons.Rounded.Thermostat,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherHot
        )
    }
    if (icon == WeatherIcon.Cold) {
        Icon(
            modifier = Modifier
                .size(32.dp),
            imageVector = Icons.Rounded.AcUnit,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherCold
        )
    }
}

@Composable
private fun Wind(icon: WeatherIcon) {
    if (icon == WeatherIcon.Storm) {
        Icon(
            modifier = Modifier
                .offset(x = 4.dp, y = 0.dp)
                .size(24.dp),
            imageVector = Icons.Rounded.Air,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherWindDark
        )
    }
    if (icon == WeatherIcon.Wind || icon == WeatherIcon.Storm) {
        Icon(
            modifier = Modifier
                .size(if(icon == WeatherIcon.Wind) 32.dp else 24.dp),
            imageVector = Icons.Rounded.Air,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherWind
        )
    }
}

@Composable
private fun Fog(icon: WeatherIcon) {

    if (icon != WeatherIcon.Haze && icon != WeatherIcon.Fog) {
        return
    }

    Icon(
        modifier = Modifier
            .size(24.dp)
            .offset(x = 3.dp, y = 4.dp),
        imageVector = Icons.Rounded.WeatherFog,
        contentDescription = null,
        tint = MaterialTheme.colors.weatherFog
    )
}

@Composable
private fun LightningBolts(icon: WeatherIcon) {

    if (icon != WeatherIcon.ThunderstormWithRain
        && icon != WeatherIcon.HeavyThunderstormWithRain
        && icon != WeatherIcon.Thunderstorm
        && icon != WeatherIcon.HeavyThunderstorm
    ) {
        return
    }

    val isHeavy = icon == WeatherIcon.HeavyThunderstormWithRain || icon == WeatherIcon.HeavyThunderstorm

    Icon(
        modifier = Modifier
            .size(12.dp)
            .offset(x = if (isHeavy) 4.dp else 1.dp, y = 6.dp),
        imageVector = Icons.Rounded.Bolt,
        contentDescription = null,
        tint = MaterialTheme.colors.weatherBolt
    )

    if  (icon == WeatherIcon.HeavyThunderstorm || icon == WeatherIcon.HeavyThunderstormWithRain) {
        Icon(
            modifier = Modifier
                .size(10.dp)
                .offset(x = -3.dp, y = 6.dp),
            imageVector = Icons.Rounded.Bolt,
            contentDescription = null,
            tint = MaterialTheme.colors.weatherBolt
        )
    }
}