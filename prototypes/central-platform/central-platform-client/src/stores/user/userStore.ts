import { defineStore } from "pinia";

import { type RemovableRef, useSessionStorage } from "@vueuse/core";

import type { User, MinifiedUser, UserCredentials } from "~/types/User/User";

import { useToast } from "vue-toastification";

export const useUserStore = defineStore(
  "user",
  () => {
    const user: RemovableRef<User> = useSessionStorage<User>(
      "user",
      {} as User
    );
    const toast = useToast();
    const getUser = computed(() => user);

    async function authenticate(credentials: UserCredentials): Promise<void> {
      const { data } = await useAsyncData<{
        access_token: string;
        refresh_token: string;
        user: User;
      }>("user", () =>
        $fetch("http://localhost:8080/api/auth/authenticate", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: credentials,
        })
      );

      if (data) {
        if (data.value?.access_token) {
          user.value = data.value.user;
          const userToken = useCookie("userToken", {
            secure: true,
            sameSite: "strict",
            maxAge: 86400,
          });
          const userRole = useCookie("userRole", {
            secure: true,
            sameSite: "strict",
            maxAge: 86400,
          });
          userRole.value = data.value.user.role
          userToken.value = data.value.access_token;
        }
      }
    }

    async function register(newUser: MinifiedUser): Promise<void> {
      try {
        const { data } = await useAsyncData<{
          access_token: string;
          refresh_token: string;
          user: User;
        }>("user", async () =>
          await $fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: {
              email: newUser.email,
              firstname: newUser.firstName,
              lastname: newUser.lastName,
              password: newUser.password,
              role: "USER",
            },
          })
        );
        console.log('test'+data.value)
        if (data && data.value?.access_token) {
          user.value = data.value.user;
          const userToken = useCookie("userToken", {
            secure: true,
            sameSite: "strict",
            maxAge: 86400,
          });
          const userRole = useCookie("userRole", {
            secure: true,
            sameSite: "strict",
            maxAge: 86400,
          });
          userToken.value = data.value.access_token;
          userRole.value = data.value.user.role;
          
          const userOrgApplication = await useAsyncData<{}>(
            "userOrganizationApplication",
            async () =>
              await $fetch(
                "http://localhost:8080/api/user/user-assign-request/new",
                {
                  method: "POST",
                  headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + userToken.value,
                  },
                  body: {
                    type: "create",
                    role: "USER",
                    organizationId: newUser.organizationId,
                  },
                }
              )
          );
          console.log('test2')
          if (userOrgApplication.data) {
            console.log('test3')
            toast.success("Registration successful");
            navigateTo("/home");
          } else {
            toast.error("Failed to create user organization request");
          }
        } else {
          toast.error("Could not create user");
        }
      } catch (error) {
        console.error(error);
        toast.error("Error during registration");
      }
    }
    

    return {
      getUser,
      authenticate,
      register,
      user,
    };
  },
  {
    persist: false,
  }
);
