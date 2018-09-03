import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';

type EntityResponseType = HttpResponse<IOrgAdminPulse>;
type EntityArrayResponseType = HttpResponse<IOrgAdminPulse[]>;

@Injectable({ providedIn: 'root' })
export class OrgAdminPulseService {
    private resourceUrl = SERVER_API_URL + 'api/org-admins';

    constructor(private http: HttpClient) {}

    create(orgAdmin: IOrgAdminPulse): Observable<EntityResponseType> {
        return this.http.post<IOrgAdminPulse>(this.resourceUrl, orgAdmin, { observe: 'response' });
    }

    update(orgAdmin: IOrgAdminPulse): Observable<EntityResponseType> {
        return this.http.put<IOrgAdminPulse>(this.resourceUrl, orgAdmin, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrgAdminPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrgAdminPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
