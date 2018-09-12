import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IUserExtPulse } from 'app/shared/model/user-ext-pulse.model';

type EntityResponseType = HttpResponse<IUserExtPulse>;
type EntityArrayResponseType = HttpResponse<IUserExtPulse[]>;

@Injectable({ providedIn: 'root' })
export class UserExtPulseService {
    private resourceUrl = SERVER_API_URL + 'api/user-exts';

    constructor(private http: HttpClient) {}

    create(userExt: IUserExtPulse): Observable<EntityResponseType> {
        return this.http.post<IUserExtPulse>(this.resourceUrl, userExt, { observe: 'response' });
    }

    update(userExt: IUserExtPulse): Observable<EntityResponseType> {
        return this.http.put<IUserExtPulse>(this.resourceUrl, userExt, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IUserExtPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IUserExtPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
