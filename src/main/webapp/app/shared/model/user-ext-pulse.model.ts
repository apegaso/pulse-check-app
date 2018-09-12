export interface IUserExtPulse {
    id?: number;
    jobRole?: string;
    email?: string;
    userId?: number;
    organizationId?: number;
    clientLeadId?: number;
    orgAdminId?: number;
    participantId?: number;
}

export class UserExtPulse implements IUserExtPulse {
    constructor(
        public id?: number,
        public jobRole?: string,
        public email?: string,
        public userId?: number,
        public organizationId?: number,
        public clientLeadId?: number,
        public orgAdminId?: number,
        public participantId?: number
    ) {}
}
