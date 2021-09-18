package de.mm20.launcher2.unitconverter.converters

import android.content.Context
import de.mm20.launcher2.unitconverter.Dimension
import de.mm20.launcher2.unitconverter.MeasureUnit
import de.mm20.launcher2.unitconverter.R

class VelocityConverter(context: Context) : Converter() {
    override val dimension = Dimension.Velocity

    override val standardUnits = listOf(
            MeasureUnit(
                    1.0,
                    context.getString(R.string.unit_meter_per_second_symbol),
                    R.plurals.unit_meter_per_second
            ),
            MeasureUnit(
                    3.6,
                    context.getString(R.string.unit_kilometer_per_hour_symbol),
                    R.plurals.unit_kilometer_per_hour
            ),
            MeasureUnit(
                    3600.0 / 1609.344,
                    context.getString(R.string.unit_mile_per_hour_symbol),
                    R.plurals.unit_mile_per_hour
            ),
            MeasureUnit(
                    3600.0 / 1852.0,
                    context.getString(R.string.unit_knot_symbol),
                    R.plurals.unit_knot
            )
    )

}