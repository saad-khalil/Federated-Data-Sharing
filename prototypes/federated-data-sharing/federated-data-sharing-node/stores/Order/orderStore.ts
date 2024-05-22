import { defineStore } from "pinia"
import { useSessionStorage, type RemovableRef } from "@vueuse/core"
import { useToast } from "vue-toastification";

import type { Order } from "~/types/Order/Order";

export const useOrderStore = defineStore("order", () => {

    const { $trpcClient } = useNuxtApp();
    const { orders } = $trpcClient
    const toast = useToast();

    const orders_app : RemovableRef<Order[]> =
        useSessionStorage<Order[]>("others_app", [] as Order[])

    const getOrders : ComputedRef<RemovableRef<Order[]>> = computed(() => orders_app)

    async function create_order(order : {
        organization_id: number,
        source_location_id: number,
        destination_location_id: number,
        status: string,
        security: "private" | "subscribe" | "public"
    }) {

        const { data } = await orders.addOrder.useQuery({
            organization_id: order.organization_id,
            source_location_id: order.source_location_id,
            destination_location_id: order.destination_location_id,
            status: order.status,
            security: order.security
        })

        return data.value?.response.payload

    }
    return {
        getOrders, create_order
    }
}, {
    persist: true
})