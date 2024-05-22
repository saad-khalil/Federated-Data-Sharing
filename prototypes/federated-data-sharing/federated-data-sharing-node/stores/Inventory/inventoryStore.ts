import { defineStore } from "pinia"
import { useToast } from "vue-toastification";

import type { Inventory } from "~/types/Inventory/Inventory"

export const useInventoryStore = defineStore("inventory", () => {

    const { $trpcClient } = useNuxtApp();
    const { inventories } = $trpcClient
    const toast = useToast();

    async function create_inventory(inventory_details : {
        item_type: string,
        inventory_name: string,
        quantity: number,
        location_id: number,
        security: "private" | "subscribe" | "public"
    }){
        const { data } = await inventories.addInventory.useQuery({
            item_type: inventory_details.item_type,
            inventory_name: inventory_details.inventory_name,
            quantity:  inventory_details.quantity,
            location_id: inventory_details.location_id,
            security: inventory_details.security
        })

        return data.value?.response.payload
    }

    return {
        create_inventory,
    }
}, {
    persist: true
})