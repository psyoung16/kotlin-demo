package org.psy.demo.core.sdui.mapper.factory

import org.psy.demo.app.sdui.translator.BaseMixin
import org.psy.demo.app.sdui.translator.SupportingMixin


typealias ItemCompositorFactory<T, S> = (T) -> S

typealias DomainBaseMixinMapper<T> = (T) -> BaseMixin
typealias DomainSupportingMixinMapper<T,S> = (T) -> SupportingMixin<S>
