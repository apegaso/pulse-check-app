/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { ClientLeadPulseDeleteDialogComponent } from 'app/entities/client-lead-pulse/client-lead-pulse-delete-dialog.component';
import { ClientLeadPulseService } from 'app/entities/client-lead-pulse/client-lead-pulse.service';

describe('Component Tests', () => {
    describe('ClientLeadPulse Management Delete Component', () => {
        let comp: ClientLeadPulseDeleteDialogComponent;
        let fixture: ComponentFixture<ClientLeadPulseDeleteDialogComponent>;
        let service: ClientLeadPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [ClientLeadPulseDeleteDialogComponent]
            })
                .overrideTemplate(ClientLeadPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClientLeadPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClientLeadPulseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
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
            ));
        });
    });
});
