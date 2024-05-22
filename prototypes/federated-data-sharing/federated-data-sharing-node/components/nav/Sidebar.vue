<script lang="ts" setup>
    import { TabGroup, TabList, Tab, TabPanels, TabPanel, Listbox, ListboxButton, ListboxOption, ListboxOptions } from '@headlessui/vue'
    import { PlusIcon } from "@heroicons/vue/24/solid"
    import { useOrganizationStore } from "~/stores/Organization/organizationStore";

    const route = useRoute()
    const organizationStore = useOrganizationStore();

    const { getOrganizations, addDummy, subscribe_to_organization, setCurrentOrganization, get_selected_organization } = organizationStore
    const isOrganizationModalOpen: Ref<boolean> = ref<boolean>(false)
    const categories : Ref<{
        Create: never[];
        Subscribe: never[];
    }> = ref({
        Create: [],
        Subscribe: [],
    })

    const organization_options = [
        {id: 1, name: "brewery", unavailable: false},
        {id: 2, name: "supermarket", unavailable: false},
        {id: 3, name: "whole_seller", unavailable: false},
    ]

    const security_options = [
        {id: 1, name: "public", unavailable: false},
        {id: 2, name: "private", unavailable: false},
        {id: 3, name: "subscribe", unavailable: false},
    ] as const

    const organization_type : Ref<{
        id: number;
        name: string;
        unavailable: boolean;
    }> = ref(organization_options[0])

    const organization_name : Ref<string> = ref<string>("")
    const organization_security  = ref(security_options[0])
    const organization_secret_key : Ref<string> = ref<string>("")

    function generate_new_node(){
        addDummy(organization_name.value, organization_type.value.name, organization_security.value.name)
        isOrganizationModalOpen.value = !isOrganizationModalOpen.value
    }

    function subscribe(secret_key: string) {
        subscribe_to_organization(Number(route.params.id), secret_key)
        isOrganizationModalOpen.value = !isOrganizationModalOpen.value
    }


</script>

<template>
    <div>
        <aside class="h-screen w-[400px] bg-neutral-900 border-r-2 border-neutral-700 flex flex-row justify-center items-center overflow-hidden">
            <div class="w-[110px] h-full overflow-y-scroll no-scrollbar">
                <div class="flex flex-col justify-start items-center py-2 gap-2">
                    <NuxtLink
                    v-for="(organization, index) in getOrganizations"
                    :key="index"
                    @click.prevent="setCurrentOrganization(organization)"
                    :to="`/home/institution/${organization.organization_id}`"
                    class="border-2 bg-transparent border-emerald-300/20 hover:border-emerald-300 transition-colors duration-500 rounded-md w-[70px] h-[70px] min-w-[70px] flex flex-col items-center justify-center select-none text-white"
                    >
                     {{ useAbreviation(organization.name) }}
                    </NuxtLink>
                    <div
                    @click="isOrganizationModalOpen = !isOrganizationModalOpen"
                    class="border-2 bg-transparent border-emerald-300/20 hover:border-emerald-300 transition-colors duration-500 rounded-md w-[70px] h-[70px] min-w-[70px] flex flex-col items-center justify-center select-none text-white"
                    >
                        <PlusIcon class="group-hover:text-neutral-900 w-6 text-neutral-400 transition-colors duration-500" />
                    </div>
                </div>
            </div>
            <div

            class="w-full h-full flex flex-col items-center justify-between border-l-2 border-neutral-700">
                <div class="w-full h-full pb-10 overflow-hidden">
                    <div class=" border-b-2 border-neutral-700 h-10 w-full flex flex-col items-center justify-center">
                        <h1 class="font-semibold text-md select-none text-white">
                            Locations
                        </h1>
                    </div>

                    <div class="w-full h-full flex flex-col justify-start gap-2 mt-1 py-4 overflow-y-scroll no-scrollbar">
                        <div
                        class="text-white w-11/12  bg-neutral-800 mx-auto rounded-md border-2 border-emerald-500/20 hover:border-emerald-500 p-3 duration-300 transition"
                        v-for="(location, index) in get_selected_organization.locations"
                        :key="index"
                        >
                            <h1>Name: {{ location }}</h1>

                            <p>Organiation: </p>
                        </div>
                    </div>
                </div>
            </div>
        </aside>
        <LazyModal
        :is-modal-open="isOrganizationModalOpen"
        @update:open="isOrganizationModalOpen = $event"
        >
            <div class="w-full max-w-md px-2 py-16 sm:px-0">
                <TabGroup>
                    <TabList class="flex space-x-1 rounded-xl bg-neutral-800 p-1">
                        <Tab
                        v-for="category in Object.keys(categories)"
                        as="template"
                        :key="category"
                        v-slot="{ selected }"
                        >
                        <button
                            :class="[
                            'w-full rounded-lg py-2.5 text-sm font-medium leading-5 text-emerald-500',
                            'ring-white/60 ring-offset-2 ring-offset-emerald-500 focus:outline-none focus:ring-2',
                            selected
                                ? 'bg-bg-white/[0.12] shadow'
                                : 'text-emerald-100 hover:bg-white/[0.12] hover:text-white',
                            ]"
                        >
                            {{ category }}
                        </button>
                        </Tab>
                    </TabList>

                    <TabPanels class="mt-2">
                        <TabPanel
                        :class="[
                            'rounded-xl bg-neutral-900 p-3',
                            'ring-offset-0 focus:outline-none',
                        ]"
                        >
                            <div class="flex flex-col items-center justify-start text-white w-12/12 mx-auto">
                                <div class="mt-2 w-full">
                                    <label class="block text-neutral-300 font-semibold">Organization Name</label>
                                    <input
                                    v-model="organization_name"
                                    type="text"
                                    name=""
                                    id=""
                                    placeholder="Enter Organization Name"
                                    class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required>
                                </div>
                                <div class="w-full flex flex-col items-start justify-start py-4 relative">
                                    <h1 class="font-semibold  text-neutral-300"> Organization Type</h1>
                                    <Listbox v-model="organization_type">
                                        <ListboxButton
                                        class="w-full px-4 py-3 text-[16px] rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                                        > {{ organization_type.name }}
                                        </ListboxButton>
                                        <Transition
                                        enter-active-class="transition duration-100 ease-out"
                                        enter-from-class="transform scale-95 opacity-0"
                                        enter-to-class="transform scale-100 opacity-100"
                                        leave-active-class="transition duration-75 ease-out"
                                        leave-from-class="transform scale-100 opacity-100"
                                        leave-to-class="transform scale-95 opacity-0"
                                        >
                                            <ListboxOptions class="w-full min-w-[310px] bg-neutral-900 rounded-md absolute z-50 p-2 border-2 border-emerald-500/40 shadow-md transform-gpu translate-y-20 bottom-0 flex flex-col items-center justify-start">
                                                <ListboxOption
                                                class=" bg-white rounded-md border-2 border-emerald-500 hover:bg-emerald-500 transition duration-300 w-full py-2  text-black text-center mt-2"
                                                v-for="(type, index) in organization_options"
                                                :key="type.id"
                                                :value="type"
                                                :disabled="type.unavailable"
                                                >
                                                    {{ type.name }}
                                                </ListboxOption>
                                            </ListboxOptions>
                                        </Transition>
                                    </Listbox>
                                </div>

                                <div class="w-full flex flex-col items-start justify-start pb-4 relative">
                                    <h1 class="font-semibold  text-neutral-300"> Organization Security</h1>
                                    <Listbox v-model="organization_security">
                                        <ListboxButton
                                        class="w-full px-4 py-3 text-[16px] rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                                        > {{ organization_security.name }}
                                        </ListboxButton>
                                        <Transition
                                        enter-active-class="transition duration-100 ease-out"
                                        enter-from-class="transform scale-95 opacity-0"
                                        enter-to-class="transform scale-100 opacity-100"
                                        leave-active-class="transition duration-75 ease-out"
                                        leave-from-class="transform scale-100 opacity-100"
                                        leave-to-class="transform scale-95 opacity-0"
                                        >
                                            <ListboxOptions class="w-full min-w-[310px] bg-neutral-900 rounded-md absolute z-10 p-2 border-2 border-emerald-500/40 shadow-md transform-gpu translate-y-20 bottom-0 flex flex-col items-center justify-start">
                                                <ListboxOption
                                                class=" bg-white rounded-md border-2 border-emerald-500 hover:bg-emerald-500 transition duration-300 w-full py-2  text-black text-center"
                                                v-for="(type, index) in security_options"
                                                :key="type.id"
                                                :value="type"
                                                :disabled="type.unavailable"
                                                >
                                                    {{ type.name }}
                                                </ListboxOption>
                                            </ListboxOptions>
                                        </Transition>
                                    </Listbox>
                                </div>

                                <div
                                @click="generate_new_node()"
                                class="bg-emerald-500 rounded-md py-2 px-10 font-semibold cursor-pointer w-full text-center"
                                >
                                    Add
                                </div>
                            </div>

                        </TabPanel>

                        <TabPanel
                        :class="[
                            'rounded-xl bg-neutral-900 p-3',
                            'ring-offset-0 focus:outline-none',
                        ]"
                        >
                            <label class="block text-neutral-300 font-semibold">Organization Secret Key</label>
                            <input
                                v-model="organization_name"
                                type="text"
                                name=""
                                id=""
                                placeholder="Enter Organization Secret Key"
                                class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900" autofocus required
                            >

                            <div
                            @click="subscribe(organization_secret_key)"
                            class="bg-emerald-500 rounded-md py-2 px-10 font-semibold cursor-pointer text-center text-white mt-3"
                            >
                                Subscribe
                            </div>

                        </TabPanel>
                    </TabPanels>
                </TabGroup>
            </div>
        </LazyModal>
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
