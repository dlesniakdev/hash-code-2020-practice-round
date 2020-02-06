data class Input(
    val maxSlices: Long,
    val differentTypes: Long,
    val types: List<Long>
) {
    override fun toString(): String {
        return "Input(maxSlices=$maxSlices, differentTypes=$differentTypes, types.size=${types.size})"
    }
}