package com.playtech.hire.test;

import com.playtech.hire.auth.Accounts;

import java.io.*;
import java.util.concurrent.Callable;

public class CreateInitial implements Callable<Accounts> {

	@Override
	public Accounts call() throws Exception {
		return createInitial();
	}

	private static Accounts createInitial() throws IOException {
		Accounts a = new Accounts();
		File initialFile = new File("initial.acc");

		try (InputStream f = new FileInputStream(initialFile)) {
			a.getStorage().read(f);
			if (a.size() > 0) {
				Assert.assertEquals(a.auth("xxs", "noPass0").getUid(), "xxs");
				return a;
			}
		} catch (FileNotFoundException _skip) {
		}

		a.create("xxs", "noPass0");
		a.create("bin", "-txtBin");

		try (OutputStream out = new FileOutputStream(initialFile)) {
			a.getStorage().write(out);
		}
		return a;
	}
}
