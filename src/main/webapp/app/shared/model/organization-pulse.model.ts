import { IEventPulse } from 'app/shared/model//event-pulse.model';
import { IUserExtPulse } from 'app/shared/model//user-ext-pulse.model';
import { IOrgAdminPulse } from 'app/shared/model//org-admin-pulse.model';

export interface IOrganizationPulse {
    id?: number;
    organizationName?: string;
    events?: IEventPulse[];
    users?: IUserExtPulse[];
    admins?: IOrgAdminPulse[];
}

export class OrganizationPulse implements IOrganizationPulse {
    constructor(
        public id?: number,
        public organizationName?: string,
        public events?: IEventPulse[],
        public users?: IUserExtPulse[],
        public admins?: IOrgAdminPulse[]
    ) {}
}
