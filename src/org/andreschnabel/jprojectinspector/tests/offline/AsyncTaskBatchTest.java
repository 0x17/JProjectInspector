package org.andreschnabel.jprojectinspector.tests.offline;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.utilities.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.AsyncTaskBatch;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.junit.Before;
import org.junit.Test;

public class AsyncTaskBatchTest {

	private AsyncTaskBatch<Integer> batch;
	private volatile int accum = 0;

	@Before
	public void setUp() throws Exception {
		accum = 0;
		batch = new AsyncTaskBatch<Integer>(2);
		batch.add(new AsyncTask<Integer>() {
			@Override
			public void onFinished(Integer result) {
				Helpers.log("Task 1 finished with" + result + "!");
				accum += result;
			}
			@Override
			public Integer doInBackground() {
				return 23;
			}
		});
		batch.add(new AsyncTask<Integer>() {
			@Override
			public void onFinished(Integer result) {
				Helpers.log("Task 2 finished with" + result + "!");
				accum += result;
			}
			@Override
			public Integer doInBackground() {
				return 42;
			}
		});
	}

	@Test
	public void testExecuteOkay() throws Exception {
		batch.execute();
		accum++;
		Thread.sleep(500);
		Assert.assertEquals(23+42+1, accum);
	}

	@Test
	public void testExecuteAborted() throws Exception {
		batch.execute();
		batch.dipose();
		Assert.assertEquals(0, accum);
	}
}
