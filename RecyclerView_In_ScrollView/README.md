# RecyclerView inside a ScrollView Example

* This example demostrates how to put a RecyclerView into a ScrollView
* Need to use android.support.v4.widget.NestedScrollView so that you can put in a RecyclerView
* The height of RecyclerView is wrap_content because I don't want RecyclerView to scroll (lost the advantages of RecyclerView). This is just for special purpose
* In xml, put ```android:nestedScrollingEnabled="false``` to make scrolling much smoother
