package victoria.bakhaeva.pathfindermarket.ui.theme

import androidx.compose.ui.graphics.Color

// Общая палитра — из картинки рынка

// Янтарь (свет фонарей)
val AmberLight = Color(0xFFC4781A)
val AmberDark  = Color(0xFFE8A040)   // ярче на тёмном фоне

// Сталь (оружие, лёд)
val SteelLight = Color(0xFF4A6878)
val SteelDark  = Color(0xFF7AA0B8)

// Дерево (прилавки, плащи)
val WoodLight  = Color(0xFF6A4020)
val WoodDark   = Color(0xFF3A2818)

// Поверхности
val ParchmentBg      = Color(0xFFF0E8D8)  // фон light
val ParchmentSurface = Color(0xFFE4D4BC)  // карточки light
val ParchmentVariant = Color(0xFFCCB898)  // акцент light

val NightBg      = Color(0xFF161E28)      // фон dark
val NightSurface = Color(0xFF20283A)      // карточки dark
val NightVariant = Color(0xFF2C3848)      // акцент dark

// Текст
val InkDark   = Color(0xFF1A0E06)   // текст на светлом
val InkLight  = Color(0xFFE0CDB0)   // текст на тёмном

// Ошибка/огонь
val EmberLight = Color(0xFFB83010)
val EmberDark  = Color(0xFFE04820)

// aliases
val Gold     = AmberLight
val DarkGold = AmberDark

// имена из HomeScreen / LoadingScreen
val NightSky     = NightBg
val DimAmber     = AmberLight
val DawnInk      = InkLight
val ShadowBrown  = WoodDark
val LanternAmber = AmberLight
val DarkAmber    = AmberDark
val SteelBlue    = SteelLight
