data class Output(val slices: Long,
                  val types: Long,
                  val lastAdded: Long,
                  var picks: List<Int>) {
    override fun toString(): String {
        return "Output(slices=$slices, types=$types, lastAdded=$lastAdded, picks.size=${picks.size})"
    }
}