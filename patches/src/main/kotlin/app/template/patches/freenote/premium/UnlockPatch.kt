package app.template.patches.freenote.premium

import app.morphe.patcher.patch.bytecodePatch
import app.template.patches.shared.Constants.COMPATIBILITY_FREENOTE

@Suppress("unused")
val unlockPatch = bytecodePatch(
    name = "Unlock Premium",
    description = "Unlocks premium features"
) {
    compatibleWith(COMPATIBILITY_FREENOTE)

    execute {
    }
}
