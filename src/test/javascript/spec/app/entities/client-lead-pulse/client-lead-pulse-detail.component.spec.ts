/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { ClientLeadPulseDetailComponent } from 'app/entities/client-lead-pulse/client-lead-pulse-detail.component';
import { ClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';

describe('Component Tests', () => {
    describe('ClientLeadPulse Management Detail Component', () => {
        let comp: ClientLeadPulseDetailComponent;
        let fixture: ComponentFixture<ClientLeadPulseDetailComponent>;
        const route = ({ data: of({ clientLead: new ClientLeadPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [ClientLeadPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClientLeadPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientLeadPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.clientLead).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
