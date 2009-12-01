package com.nanosim.client.event;

import com.nanosim.model.Person;

public interface ISigninHandler {
	void OnSuccess(Person p);
}
