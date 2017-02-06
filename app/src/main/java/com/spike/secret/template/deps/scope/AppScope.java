package com.spike.secret.template.deps.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Shyam on 2/4/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface AppScope {
}
