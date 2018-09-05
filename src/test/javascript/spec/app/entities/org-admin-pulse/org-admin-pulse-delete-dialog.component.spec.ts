/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrgAdminPulseDeleteDialogComponent } from 'app/entities/org-admin-pulse/org-admin-pulse-delete-dialog.component';
import { OrgAdminPulseService } from 'app/entities/org-admin-pulse/org-admin-pulse.service';

describe('Component Tests', () => {
    describe('OrgAdminPulse Management Delete Component', () => {
        let comp: OrgAdminPulseDeleteDialogComponent;
        let fixture: ComponentFixture<OrgAdminPulseDeleteDialogComponent>;
        let service: OrgAdminPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrgAdminPulseDeleteDialogComponent]
            })
                .overrideTemplate(OrgAdminPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrgAdminPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrgAdminPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
