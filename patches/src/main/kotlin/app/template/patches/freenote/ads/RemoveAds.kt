package app.template.patches.freenote.ads

import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.shared.Constants.COMPATIBILITY_FREENOTE

@Suppress("unused")
val removeAdsPatch = bytecodePatch(
    name = "Remove Ads",
    description = "Removes ads"
) {
    compatibleWith(COMPATIBILITY_FREENOTE)

    execute {
    }
}
