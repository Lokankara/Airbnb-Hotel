import {computed, inject, Injectable, signal, WritableSignal} from '@angular/core';
import {HttpClient, HttpParams, HttpStatusCode} from "@angular/common/http";
import {User} from "./user.model";
import {Location} from "@angular/common";
import {State} from "./state.model";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  http = inject(HttpClient);
  location = inject(Location);
  notConnected = "NOT_CONNECTED"

  private fetchUser$: WritableSignal<State<User>> = signal(State.Builder<User>().forSuccess({email: this.notConnected}));

  fetchUser = computed(() => this.fetchUser$());

  fetch(reSync: boolean): void {
    this.fetchHttpUser(reSync).subscribe({
      next: user => this.fetchUser$.set(State.Builder<User>().forSuccess(user)),
      error: err => {
        if (err.status === HttpStatusCode.Unauthorized && this.isAuthenticated()) {
          this.fetchUser$.set(State.Builder<User>().forSuccess({email: this.notConnected}));
        } else {
          this.fetchUser$.set(State.Builder<User>().forError(err))
        }
      }
    });
  }

  private fetchHttpUser(reSync: boolean): Observable<User> {
    const params = new HttpParams().set('forceResync', reSync);
    return this.http.get<User>(`${environment.API_URL}/auth/get-authenticated-user`, {params})
  }

  login(): void {
    location.href = `${location.origin}${this.location.prepareExternalUrl("oauth2/authorization/okta")}`
  }

  logout(): void {
    this.http.post(`${environment.API_URL}/auth/logout`, {})
      .subscribe({
        next: (response: any) => {
          this.fetchUser$.set(State.Builder<User>()
            .forSuccess({email: this.notConnected}));
          location.href = response.logoutUrl
        }
      })
  }

  hasAnyAuthority(authorities: string[] | string): boolean {
    if (this.fetchUser$().value!.email === this.notConnected) {
      return false;
    }
    if (!Array.isArray(authorities)) {
      authorities = [authorities];
    }
    return this.fetchUser$().value!.authorities!.some((authority: string) => authorities.includes(authority));
  }

  isAuthenticated() {
    return this.fetchUser$().value ? this.fetchUser$().value!.email !== this.notConnected : false;
  }
}
