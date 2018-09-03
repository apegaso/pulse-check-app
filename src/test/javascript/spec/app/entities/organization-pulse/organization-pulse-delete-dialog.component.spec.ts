/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrganizationPulseDeleteDialogComponent } from 'app/entities/organization-pulse/organization-pulse-delete-dialog.component';
import { OrganizationPulseService } from 'app/entities/organization-pulse/organization-pulse.service';

describe('Component Tests', () => {
    describe('OrganizationPulse Management Delete Component', () => {
        let comp: OrganizationPulseDeleteDialogComponent;
        let fixture: ComponentFixture<OrganizationPulseDeleteDialogComponent>;
        let service: OrganizationPulseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrganizationPulseDeleteDialogComponent]
            })
                .overrideTemplate(OrganizationPulseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrganizationPulseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrganizationPulseService);
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
