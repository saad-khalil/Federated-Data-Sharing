<script setup lang="ts">

    import { useInventoryStore } from '~/stores/Inventory/inventoryStore';
    import { useLocationStore } from '~/stores/Location/locationStore';
    import { useFederatedNodeStore } from '~/stores/Node/nodeStore';
    import { useOrderStore } from '~/stores/Order/orderStore';
    import { useOrganizationStore } from '~/stores/Organization/organizationStore';
    import { useUserStore } from '~/stores/User/userStore';
    import { useToast } from 'vue-toastification'

    const organizationStore = useOrganizationStore();
    const userStore = useUserStore();
    const inventoryStore = useInventoryStore();
    const locationStore = useLocationStore();
    const nodeStore = useFederatedNodeStore();
    const orderStore = useOrderStore()
    const toast = useToast()
    const { create_user, user } = userStore
    const { create_inventory } = inventoryStore
    const { create_location } = locationStore
    const { create_node } = nodeStore
    const { create_order } = orderStore
    const { addSampleOrg, addDummy } =  organizationStore
    const isSidemenuOpen : Ref<boolean> = ref<boolean>(false);

    useHead({
        title: "Federated - Dashboard"
    })

    onMounted(() => {
        if(user === undefined || user.email == undefined){
            navigateTo("/")
        }
    })


    const content = ref({
        order: {
            title: "Orders",
            content: {
                organization_id: 0,
                source_location_id: 0,
                destination_location_id: 0,
                status: "",
                security: ""
            }
        },
        order_item: {
            title: "Items",
            content: {
                item_type: '',
                requested_quantity: 0,
                actual_quanitity: 0,
                security: '',
                order_id: 0
            }
        },
        inventory: {
            title: "Inventories",
            content: {
                item_type: "",
                inventory_name: "",
                quantity: 0,
                location_id: 0,
                security: ""
            }
        },
        location: {
            title: "Locations",
            content: {
                name: "",
                address: "",
                latitude: "",
                longitude: "",
                security: "",
                organization_id: 0
            }
        },
        node: {
            title: "Nodes",
            content: {
                name: "",
                owner: 0,
                security: ""
            }
        },
        user: {
            title: "Users",
            content: {
                email: "",
                password: "",
                name: "",
                secret_key: ""
            }
        },
        organization: {
            title: "Organizations",
            content: {
                organization_name: "",
                organization_type: "",
                security: ""
            }
        }
    })

    const selected_content : Ref<{
        title: string,
        content: {
            name?: string,
            type?: string,
            email?: string,
            password?: string,
            owner?: string,
            location_name? : string,
            secret_key?: string
            source_location_id?: number,
            location_id? : number,
            inventory_name?: string,
            quantity?: number,
            latitude? : string,
            address? : string,
            longitude? : string,
            destination_location_id?: number,
            status? : string,
            organization_id?: number,
            actual_quantity?: number,
            requested_quantity?: number,
            node_name? : string,
            organization_name? : string,
            organization_type?: string,
            item_type?: string,
            security?: "public" | "subscribe" | "private"
        }
    }> = ref<{
        title: string,
        content: {

        }
    }>({
        title: "",
        content: {

        }
    })

    function select_property(property : {
        title: string,
        content: any
    }) {
        selected_content.value = property
        isSidemenuOpen.value = !isSidemenuOpen.value
    }

    function update_property(prop_name: string, prop_value : string){
        (selected_content.value.content as Record<string, any>)[prop_name] = prop_value
    }

    async function create_element(content: {
        title: string,
        content: {
            name?: string,
            type?: string,
            email?: string,
            password?: string,
            owner?: string,
            secret_key?: string
            source_location_id?: number,
            location_id? : number,
            location_name?: string,
            inventory_name?: string,
            quantity?: number,
            latitude? : string,
            address? : string,
            longitude? : string,
            destination_location_id?: number,
            status? : string,
            actual_quantity?: number,
            organization_id?: number,
            requested_quantity?: number,
            item_type?: string,
            node_name?: string,
            organization_name?: string,
            organization_type? : string,
            security?: "public" | "subscribe" | "private"

        }
    }){
        toast.info("Sending Request")
        switch (content.title) {
            case "Orders":
                const new_order = await create_order({
                    organization_id: Number(content.content.organization_id),
                    source_location_id: Number(content.content.source_location_id),
                    destination_location_id: Number(content.content.destination_location_id),
                    status: content.content.status as string,
                    security: content.content.security as "public" | "subscribe" | "private"
                })
                toast.success("Successfully Created Order")
                break;

            case "Items":
                break;

            case "Inventories":
                const new_inventory = await create_inventory({
                    item_type: content.content.item_type as string,
                    inventory_name: content.content.inventory_name as string,
                    quantity: Number(content.content.quantity),
                    location_id: Number(content.content.location_id),
                    security: content.content.security as "public" | "subscribe" | "private"
                })
                toast.success("Successfully Created Inventories")
                break;

            case "Locations":
                await create_location({
                    name: content.content.name as string,
                    address: content.content.address as string,
                    latitude: content.content.latitude as string,
                    longitude: content.content.longitude as string,
                    organization_id: Number(content.content.organization_id),
                    security: content.content.security as "public" | "subscribe" | "private"
                })
                toast.success("Successfully Locations")
                break;
            case "Users":
                await create_user({
                    name: content.content.name as string,
                    password: content.content.password as string,
                    email: content.content.email as string,
                    secret_key: content.content.secret_key as string
                })
                break;

            case "Organizations":

                await addDummy(content.content.organization_name as string, content.content.organization_type as string, content.content.security as "public" | "private" | "subscribe")
                toast.success("Successfully Created Organization")
                break;
            default:
                break;
        }
    }
</script>

<template>
    <div>
        <div class="bg-neutral-900 min-h-screen flex items-start justify-center">
            <div class="bg-neutral-900 flex-1 flex flex-col">
                <!-- Navigation -->
                <div class="bg-neutral-900 px-2 lg:px-4 py-2 lg:py-10 sm:rounded-xl flex lg:flex-col justify-between">

                    <div class="mb-10 sm:mb-0 mt-10 grid gap-4 grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4">
                        <div
                        @click="select_property(content.organization)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Organization</h4>
                        </div>

                        <div
                        @click="select_property(content.user)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Users</h4>
                        </div>

                        <div
                        @click="select_property(content.location)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Location</h4>
                        </div>

                        <div
                        @click="select_property(content.inventory)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Inventory</h4>
                        </div>

                        <div
                        @click="select_property(content.order)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Order</h4>
                        </div>

                        <div
                        @click="select_property(content.order_item)"
                        class="relative group bg-neutral-800/60 py-10 sm:py-20 px-4 flex flex-col space-y-2 items-center rounded-md hover:bg-neutral-700 hover:smooth-hover transition duration-300">
                            <img class="w-20 h-20 object-cover object-center rounded-full" src="https://images.unsplash.com/photo-1547592180-85f173990554?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1170&q=80" alt="cuisine" />
                            <h4 class="text-white text-2xl font-bold capitalize text-center">Odrer Item</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <LazySlideover
        :title="`${selected_content.title}`"
        :is-slide-open="isSidemenuOpen"
        @update:slide-open="isSidemenuOpen = $event"
        >
            <div class="flex flex-col justify-center items-center w-10/12">
                <LazyFormInput
                    v-for="(part, index) in selected_content.content"
                    :label="index"
                    @change="update_property($event.label, $event.value)"
                />

                <div
                @click="create_element(selected_content)"
                class="w-full px-4 py-3 rounded-lg bg-emerald-400 mt-4 text-center font-semibold border-4 border-emerald-500 hover:bg-emerald-300 hover:border-emerald-400 focus:border-emerald-400 transition duration-300 focus:bg-emerald-500 focus:outline-none outline-none text-white">
                    Add
                </div>

            </div>
        </LazySlideover>
    </div>
</template>