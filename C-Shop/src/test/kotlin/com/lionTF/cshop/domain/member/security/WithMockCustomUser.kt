package com.lionTF.cshop.domain.member.security

import org.springframework.security.test.context.support.WithSecurityContext




@Retention(value = AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory::class)
annotation class WithMockCustomUser()
