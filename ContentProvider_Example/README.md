# ContentProvider Example

This example is basee on [SQLiteOpenHelper with IntentService Example](https://github.com/terracotta-ko/Android_Treasure_House/tree/master/SQLiteOpenHelper_with_IntentService_Example), namely still use IntentSerivce to insert data in worker thread.

The different part is using MyContentProvider to wrap MySQLiteOpenHelper up.  
Therefore, MainActivity uses getContentResolver() to operate database instead of MySQLiteOpenHelper.  
Using UriMatcher to create URL rules is the crucial part when implementing MyContentProvider.
