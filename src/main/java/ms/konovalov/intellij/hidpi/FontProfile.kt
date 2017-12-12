package ms.konovalov.intellij.hidpi

class FontProfile {

    var isActive: Boolean = false
    var name: String? = null
    var isOverrideGlobalFont: Boolean = false
    var globalFontSize: Int = 0
    var globalFontFamily: String? = null
    var editorFontSize: Int = 0
    var editorFontFamily: String? = null
    var consoleFontSize: Int = 0
    var consoleFontFamily: String? = null

    @java.beans.ConstructorProperties("active", "name", "overrideGlobalFont", "globalFontSize", "globalFontFamily", "editorFontSize", "editorFontFamily", "consoleFontSize", "consoleFontFamily")
    constructor(active: Boolean, name: String, overrideGlobalFont: Boolean, globalFontSize: Int, globalFontFamily: String?, editorFontSize: Int, editorFontFamily: String, consoleFontSize: Int, consoleFontFamily: String) {
        this.isActive = active
        this.name = name
        this.isOverrideGlobalFont = overrideGlobalFont
        this.globalFontSize = globalFontSize
        this.globalFontFamily = globalFontFamily
        this.editorFontSize = editorFontSize
        this.editorFontFamily = editorFontFamily
        this.consoleFontSize = consoleFontSize
        this.consoleFontFamily = consoleFontFamily
    }

    constructor() {}
}
