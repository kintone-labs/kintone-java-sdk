# BulksException

Handle error responses when using multiple bulk request

## Methods

### getResults()

**Parameter **

(none)

**Return**

ArrayList<Object\>

**Sample code**

<details class="tab-container" open>
<Summary>Get responses if exist error when using multiple bulk request</Summary>

<strong class="tab-name">Source code</strong>

<pre class="inline-code">
try {
    BulkRequestResponse bulkRequestResponse = record.addAllRecords(appID, records);
} catch (BulksException e) {
    System.out.println(e.getResults());
}

</pre>

</details>