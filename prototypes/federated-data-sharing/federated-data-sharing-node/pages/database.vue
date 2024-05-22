<script setup lang="ts">
    import { storeToRefs } from 'pinia';
    import { useOrganizationStore } from '~/stores/Organization/organizationStore';
    import { RemovableRef } from '@vueuse/core';
    import { useUserStore } from '~/stores/User/userStore';

    import type { Organization } from '~/types/Organization/Organization';
    import type { Order } from '~/types/Order/Order';
    import type { Inventory } from '~/types/Inventory/Inventory';
    import type { Location } from '~/types/Location/Location';
import { User } from '~/types/User/User';
import { FederatedNode } from '~/types/FederatedNode/FederatedNode';

    const { $trpcClient } = useNuxtApp()
    const {
        users,
        locations,
        orders,
        organizations,
        inventories,
    } = $trpcClient
    const organizationStore = useOrganizationStore();


    const { getOrganizations } = organizationStore;


    const isSidemenuOpen : Ref<boolean> = ref<boolean>(false);
    const selectedItem : Ref<unknown | null> = ref<unknown>();

    const organizations_table : Ref<Organization[]> = ref<Organization[]>([])
    const orders_table : Ref<Order[]> = ref<Order[]>([])
    const locations_table : Ref<Location[]> = ref<Location[]>([])
    const user_table : Ref<User[]> = ref<User[]>([])
    const inventory_table : Ref<Inventory[]> = ref<Inventory[]>([])
    onMounted( async () => {
        const user_result = await users.getUsers.useQuery()
        const location_result = await locations.getLocations.useQuery()
        const order_result = await orders.getOrders.useQuery()
        const organization_result = await organizations.getOrganizations.useQuery()
        const inventory_result =await inventories.getInventories.useQuery()

        user_result.data.value?.users.forEach((user) => {
            user_table.value.push(user)
        })

        location_result.data.value?.locations.forEach((location) => {
            locations_table.value.push(location)
        })

        order_result.data.value?.orders.forEach((order) => {
            orders_table.value.push(order)
        })

        organization_result.data.value?.organizations.forEach((org) => {
            organizations_table.value.push(org)
        })

        inventory_result.data.value?.inventories.forEach((inv) => {
            inventory_table.value.push(inv as Inventory)

        })
    })

    function selectItem(item: unknown) {
        selectedItem.value = item
        isSidemenuOpen.value = !isSidemenuOpen.value
    }


    const dictionary : {
        name: string,
        key: Ref<unknown[]>
    }[] = [
        {
            name: "Orders",
            key: orders_table
        },
        {
            name: "Organizations",
            key: organizations_table
        },
        {
            name: "Locations",
            key: locations_table
        }
        ,
        {
            name: "Users",
            key: user_table
        }
        ,
        {
            name: "Inventory",
            key: inventory_table
        }
    ]

    useHead({
        title: "Federated - Database"
    })

    const userStore = useUserStore()
    const { user } = userStore
    onMounted(() => {
        if(user === undefined || user.email == undefined){
            navigateTo("/")
        }
    })


</script>

<template>
    <div>
        <div class="w-full h-screen flex flex-col items-center justify-start gap-6 p-4 overflow-y-scroll no-scrollbar">
            <div
            class="bg-neutral-800 w-full min-h-[25rem] p-3 rounded-md border-2 border-transparent hover:border-emerald-400 transition duration-500 relative flex flex-col items-start justify-evenly"
            >
            <h1 class="text-white font-semibold text-[18px]">Organizations</h1>
                <div class="h-60 bg-neutral-900 rounded-md w-full overflow-y-scroll no-scrollbar border-emerald-500/30 border-2 p-2 flex flex-col gap-2">
                    <div
                    class="bg-transparent rounded-md text-white  flex flex-row justify-center items-center p-4 font-semibold"
                    >
                        <h1 class=" w-1/4">Name</h1>
                        <h1 class=" w-1/4">Type</h1>
                        <h1 class=" w-1/4">Security</h1>
                        <h1 class=" w-1/4">ID</h1>
                    </div>
                    <div
                    class="bg-transparent rounded-md hover:bg-emerald-400 transition duration-500 text-white hover:text-neutral-900 flex flex-row justify-center items-center p-4 font-semibold"
                    v-for="(item, index) in organizations_table"
                    :key="index"
                    @click="selectItem(item)"
                    >
                    <h1 class=" w-1/4">{{ item.name }} </h1>
                    <h1 class=" w-1/4"> {{ item.organization_type }} </h1>
                    <h1 class=" w-1/4"> {{ item.security }} </h1>
                    <h1 class=" w-1/4">{{ item.organization_id }} </h1>
                    </div>
                </div>
            </div>

            <div
            class="bg-neutral-800 w-full min-h-[25rem] p-3 rounded-md border-2 border-transparent hover:border-emerald-400 transition duration-500 relative flex flex-col items-start justify-evenly"
            >
            <h1 class="text-white font-semibold text-[18px]">Locations</h1>
                <div class="h-60 bg-neutral-900 rounded-md w-full overflow-y-scroll no-scrollbar border-emerald-500/30 border-2 p-2 flex flex-col gap-2">
                    <div
                    class="bg-transparent rounded-md text-white  flex flex-row justify-center items-center p-4 font-semibold"
                    >
                        <h1 class=" w-1/5">Name</h1>
                        <h1 class=" w-1/5">Address</h1>
                        <h1 class=" w-1/5">Security</h1>
                        <h1 class=" w-1/5">OID</h1>
                        <h1 class=" w-1/5">LID</h1>
                    </div>
                    <div
                    class="bg-transparent rounded-md hover:bg-emerald-400 transition duration-500 text-white hover:text-neutral-900 flex flex-row justify-center items-center p-4 font-semibold"
                    v-for="(item, index) in locations_table"
                    :key="index"
                    @click="selectItem(item)"
                    >
                        <h1 class=" w-1/5">{{ item.name }} </h1>
                        <h1 class=" w-1/5"> {{ item.address }} </h1>
                        <h1 class=" w-1/5"> {{ item.security }} </h1>
                        <h1 class=" w-1/5">{{ item.organization_id }} </h1>
                        <h1 class=" w-1/5"> {{ item.location_id }} </h1>
                    </div>
                </div>
            </div>

            <div
            class="bg-neutral-800 w-full min-h-[25rem] p-3 rounded-md border-2 border-transparent hover:border-emerald-400 transition duration-500 relative flex flex-col items-start justify-evenly"
            >
            <h1 class="text-white font-semibold text-[18px]"> Orders</h1>
                <div class="h-60 bg-neutral-900 rounded-md w-full overflow-y-scroll no-scrollbar border-emerald-500/30 border-2 p-2 flex flex-col gap-2">
                    <div
                    class="bg-transparent rounded-md text-white  flex flex-row justify-center items-center p-4 font-semibold"
                    >
                        <h1 class=" w-[14.28%]">Status</h1>
                        <h1 class=" w-[14.28%]">OID</h1>
                        <h1 class=" w-[14.28%]">Security</h1>
                        <h1 class=" w-[14.28%]">Source</h1>
                        <h1 class=" w-[14.28%]">Destination</h1>
                        <h1 class=" w-[14.28%]">Amount</h1>
                        <h1 class=" w-[14.28%]">ID</h1>

                    </div>
                    <div
                    class="bg-transparent rounded-md hover:bg-emerald-400 transition duration-500 text-white hover:text-neutral-900 flex flex-row justify-center items-center p-4 font-semibold"
                    v-for="(item, index) in orders_table"
                    :key="index"
                    @click="selectItem(item)"
                    >
                        <h1 class=" w-[14.28%]">{{ item.status }}</h1>
                        <h1 class=" w-[14.28%]">{{ item.organization_id}}</h1>
                        <h1 class=" w-[14.28%]">{{ item.security }}</h1>
                        <h1 class=" w-[14.28%]">{{ item.source_location_id }}</h1>
                        <h1 class=" w-[14.28%]">{{ item.destination_location_id}}</h1>
                        <h1 class=" w-[14.28%]">{{ item.ordered_items.length}}</h1>
                        <h1 class=" w-[14.28%]">{{ item.order_id}}</h1>
                    </div>
                </div>
            </div>

            <div
            class="bg-neutral-800 w-full min-h-[25rem] p-3 rounded-md border-2 border-transparent hover:border-emerald-400 transition duration-500 relative flex flex-col items-start justify-evenly"
            >
            <h1 class="text-white font-semibold text-[18px]"> Users</h1>
                <div class="h-60 bg-neutral-900 rounded-md w-full overflow-y-scroll no-scrollbar border-emerald-500/30 border-2 p-2 flex flex-col gap-2">
                    <div
                    class="bg-transparent rounded-md text-white  flex flex-row justify-center items-center p-4 font-semibold"
                    >
                        <h1 class=" w-1/4">Email</h1>
                        <h1 class=" w-1/4">Name</h1>
                        <h1 class=" w-1/4">OID</h1>
                        <h1 class=" w-1/4">ID</h1>
                    </div>
                    <div
                    class="bg-transparent rounded-md hover:bg-emerald-400 transition duration-500 text-white hover:text-neutral-900 flex flex-row justify-center items-center p-4 font-semibold"
                    v-for="(item, index) in user_table"
                    :key="index"
                    @click="selectItem(item)"
                    >
                        <h1 class=" w-1/4">{{ item.email }}</h1>
                        <h1 class=" w-1/4">{{ item.name}}</h1>
                        <h1 class=" w-1/4">{{ item.organization_id }}</h1>
                        <h1 class=" w-1/4">{{ item.user_id }}</h1>
                    </div>
                </div>
            </div>

            <div
            class="bg-neutral-800 w-full min-h-[25rem] p-3 rounded-md border-2 border-transparent hover:border-emerald-400 transition duration-500 relative flex flex-col items-start justify-evenly"
            >
            <h1 class="text-white font-semibold text-[18px]"> Inventories </h1>
                <div class="h-60 bg-neutral-900 rounded-md w-full overflow-y-scroll no-scrollbar border-emerald-500/30 border-2 p-2 flex flex-col gap-2">
                    <div
                    class="bg-transparent rounded-md text-white  flex flex-row justify-between items-center p-4 font-semibold"
                    >
                        <h1 class=" w-1/6">IID</h1>
                        <h1 class=" w-1/6">Name</h1>
                        <h1 class=" w-1/6">Type</h1>
                        <h1 class=" w-1/6">LID</h1>
                        <h1 class=" w-1/6">Quantity</h1>
                        <h1 class=" w-1/6">Security</h1>
                    </div>
                    <div
                    class="bg-transparent rounded-md hover:bg-emerald-400 transition duration-500 text-white hover:text-neutral-900 flex flex-row justify-between items-center p-4 font-semibold"
                    v-for="(item, index) in inventory_table"
                    :key="index"
                    @click="selectItem(item)"
                    >
                        <h1 class=" w-1/6">{{ item.inventory_id }}</h1>
                        <h1 class=" w-1/6">{{ item.inventory_name}}</h1>
                        <h1 class=" w-1/6">{{ item.item_type }}</h1>
                        <h1 class=" w-1/6">{{ item.location_id }}</h1>
                        <h1 class=" w-1/6">{{ item.quantity }}</h1>
                        <h1 class=" w-1/6">{{ item.security }}</h1>
                    </div>
                </div>
            </div>
        </div>
        <LazySlideover
            title="Account Information"
            :is-slide-open="isSidemenuOpen"
            @update:slide-open="isSidemenuOpen = $event"
            >
                <h1 class="text-white"> selected item: {{ selectedItem }}</h1>
        </LazySlideover>
    </div>
</template>

<style lang="scss" scoped>
.no-scrollbar::-webkit-scrollbar {
    display: none;
}

.no-scrollbar {
    -ms-overflow-style: none;
    scrollbar-width: none;
}
</style>