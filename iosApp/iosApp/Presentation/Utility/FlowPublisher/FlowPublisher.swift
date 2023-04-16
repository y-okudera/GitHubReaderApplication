//
//  FlowPublisher.swift
//  iosApp
//
//  Created by Yuki Okudera on 2023/04/11.
//  Copyright © 2023 orgName. All rights reserved.
//

import Combine
import shared

extension Kotlinx_coroutines_coreFlow {
    func asPublisher<T: AnyObject>() -> AnyPublisher<T, Never> {
        (FlowPublisher(flow: self) as FlowPublisher<T>).eraseToAnyPublisher()
    }
}

private struct FlowPublisher<T: Any>: Publisher {
    public typealias Output = T
    public typealias Failure = Never
    private let flow: Kotlinx_coroutines_coreFlow

    public init(flow: Kotlinx_coroutines_coreFlow) {
        self.flow = flow
    }

    public func receive<S: Subscriber>(subscriber: S) where S.Input == T, S.Failure == Failure {
        subscriber.receive(subscription: FlowSubscription(flow: flow, subscriber: subscriber))
    }

    final class FlowSubscription<S: Subscriber>: Subscription where S.Input == T, S.Failure == Failure {
        private var subscriber: S?
        private var job: Kotlinx_coroutines_coreJob?
        private let flow: Kotlinx_coroutines_coreFlow
        init(flow: Kotlinx_coroutines_coreFlow, subscriber: S) {
            self.flow = flow
            self.subscriber = subscriber
            job = FlowExtensionsKt.subscribe(
                flow,
                onEach: { item in if let item = item as? T { _ = subscriber.receive(item) }},
                onComplete: { subscriber.receive(completion: .finished) },
                onThrow: { error in debugPrint(error) }
            )
        }

        func cancel() {
            subscriber = nil
            job?.cancel(cause: nil)
        }

        func request(_ demand: Subscribers.Demand) {
        }
    }
}
