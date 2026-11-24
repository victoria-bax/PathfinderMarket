package victoria.bakhaeva.pathfindermarket.data.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import victoria.bakhaeva.pathfindermarket.domain.model.BookAlias
import victoria.bakhaeva.pathfindermarket.domain.model.EncumbranceAlias
import victoria.bakhaeva.pathfindermarket.domain.model.ProficientAlias
import victoria.bakhaeva.pathfindermarket.domain.model.RangeAlias
import java.lang.reflect.Type

internal class EncumbranceJsonDeserializer : JsonDeserializer<EncumbranceAlias> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): EncumbranceAlias? = json?.alias()?.let { alias ->
        EncumbranceAlias.entries.firstOrNull { it.value == alias }
    }
}

internal class ProficientJsonDeserializer : JsonDeserializer<ProficientAlias> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ProficientAlias? = json?.alias()?.let { alias ->
        ProficientAlias.entries.firstOrNull { it.value == alias }
    }
}

internal class RangeJsonDeserializer : JsonDeserializer<RangeAlias> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RangeAlias? = json?.alias()?.let { alias ->
        RangeAlias.entries.firstOrNull { it.value == alias }
    }
}

internal class BookAliasJsonDeserializer : JsonDeserializer<BookAlias> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): BookAlias? = json?.asString?.let { alias ->
        BookAlias.entries.firstOrNull { it.value == alias }
    }
}

private fun JsonElement.alias(): String? = when {
    isJsonPrimitive && asJsonPrimitive.isString -> asString
    isJsonObject -> asJsonObject?.get("alias")?.asString
    else -> null
}
