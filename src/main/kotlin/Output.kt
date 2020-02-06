data class Output(val slices: Long,
                  val types: Long,
                  val picks: List<Int>) {
    override fun toString(): String {
        return "Output(slices=$slices, types=$types, picks.size=${picks.size})"
    }
}