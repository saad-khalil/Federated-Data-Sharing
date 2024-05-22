<script lang="ts" setup>
import { useToast } from "vue-toastification";
import { useUserStore } from "~/stores/user/userStore";
import { useOrganizationDTOStore } from "~/stores/Organization/OrganizationStoreDTO";
import { MinifiedUser } from "~/types/User/User";

const organizationDTOStore = useOrganizationDTOStore();
const { getOrganizationDTOs } = organizationDTOStore;

definePageMeta({
  layout: "auth",
});

const isLoading = ref(true);

onBeforeMount(async () => {
  setTimeout(async () => {
    await organizationDTOStore.fetchOrganizationDTOs();
    isLoading.value = false;
  });
});

const email = ref<string | null>(null);
const organizationId = ref<string | null>(null);

const firstName = ref<string | null>(null);
const lastName = ref<string | null>(null);
const password = ref<string | null>(null);

const toast = useToast();

async function register() {
  const userStore = useUserStore();
  const { register } = userStore;

  setTimeout(() => {
    toast.info("Creating user");
  });

  await register({
    firstName: firstName.value as string,
    lastName: lastName.value as string,
    organizationId: organizationId.value,
    email: email.value as string,
    password: password.value as string,
  } as MinifiedUser);
}
</script>

<template>
  <div class="max-h-screen">
    <section
      class="bg-trnasparent min-h-screen flex items-center justify-center"
    >
      <div
        class="bg-neutral-800 p-5 flex rounded-2xl shadow-3xl border-4 border-emerald-300/10 max-w-3xl"
      >
        <div class="md:w-1/2 px-5 pt-4">
          <div class="flex flex-row items-center justify-start gap-2">
            <h2 class="text-3xl font-bold text-white">Register</h2>
          </div>
          <div class="mt-6">
            <div>
              <label class="block text-neutral-300">Email Address</label>
              <input
                v-model="email"
                type="email"
                name=""
                id="email"
                placeholder="Enter Email Address"
                class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                autofocus
                required
              />
              <div class="mt-2">
                <label class="block text-neutral-300">First name</label>
                <input
                  v-model="firstName"
                  type="text"
                  name=""
                  id="firstName"
                  placeholder="Enter First "
                  class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                  autofocus
                  required
                />
              </div>
              <div class="mt-2">
                <label class="block text-neutral-300">Last name</label>
                <input
                  v-model="lastName"
                  type="text"
                  name=""
                  id="lastName"
                  placeholder="Enter  Last Name"
                  class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                  autofocus
                  required
                />
              </div>
              <div class="mt-2">
                <label class="block text-neutral-300">Organization</label>
                <select
                  v-model="organizationId"
                  class="w-full px-4 py-3 rounded-lg bg-neutral-100 mt-2 border-4 border-transparent focus:border-emerald-400 focus:bg-white focus:outline-none transition duration-300 text-neutral-900"
                  autofocus
                  required
                >
                  <option value="" disabled>Select an organization</option>
                  <option v-for="org in getOrganizationDTOs" :value="org.id">
                    {{ org.name }}
                  </option>
                </select>
              </div>
              <div class="mt-2">
                <label class="block text-neutral-300">Password</label>
                <input
                  v-model="password"
                  type="password"
                  name=""
                  id="password"
                  placeholder="Enter Password"
                  minlength="6"
                  class="w-full px-4 py-3 text-neutral-900 rounded-lg bg-neutral-100 mt-2 border-4 focus:border-emerald-400 transition duration-300 focus:bg-white focus:outline-none"
                  required
                />
              </div>
              <button
                @click="register"
                class="mt-4 w-full py-3 bg-emerald-500 text-white rounded-lg"
              >
                Submit
              </button>
            </div>
          </div>
        </div>

        <div class="w-1/2 md:block hidden">
          <img
            src="https://images.unsplash.com/photo-1614741118887-7a4ee193a5fa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80"
            class="rounded-2xl"
            alt="page img"
          />
        </div>
      </div>
    </section>
  </div>
</template>
