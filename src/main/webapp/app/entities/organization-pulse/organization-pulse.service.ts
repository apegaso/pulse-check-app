import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrganizationPulse } from 'app/shared/model/organization-pulse.model';

type EntityResponseType = HttpResponse<IOrganizationPulse>;
type EntityArrayResponseType = HttpResponse<IOrganizationPulse[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationPulseService {
    private resourceUrl = SERVER_API_URL + 'api/organizations';

    constructor(private http: HttpClient) {}

    create(organization: IOrganizationPulse): Observable<EntityResponseType> {
        return this.http.post<IOrganizationPulse>(this.resourceUrl, organization, { observe: 'response' });
    }

    update(organization: IOrganizationPulse): Observable<EntityResponseType> {
        return this.http.put<IOrganizationPulse>(this.resourceUrl, organization, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IOrganizationPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IOrganizationPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
