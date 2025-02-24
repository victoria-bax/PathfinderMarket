package victoria.bakhaeva.pathfindermarket.data.gson

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import victoria.bakhaeva.pathfindermarket.data.model.Encumbrance
import victoria.bakhaeva.pathfindermarket.data.model.Proficient
import victoria.bakhaeva.pathfindermarket.data.model.Range
import java.lang.reflect.Type

class EncumbranceJsonDeserializer : JsonDeserializer<Encumbrance> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Encumbrance? = json?.alias()?.let { alias ->
        Encumbrance.entries.firstOrNull { it.value == alias }
    }
}

class ProficientJsonDeserializer : JsonDeserializer<Proficient> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Proficient? = json?.alias()?.let { alias ->
        Proficient.entries.firstOrNull { it.value == alias }
    }
}

class RangeJsonDeserializer : JsonDeserializer<Range> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Range? = json?.alias()?.let { alias ->
        Range.entries.firstOrNull { it.value == alias }
    }
}

private fun JsonElement.alias(): String? = asJsonObject?.get("alias")?.asString