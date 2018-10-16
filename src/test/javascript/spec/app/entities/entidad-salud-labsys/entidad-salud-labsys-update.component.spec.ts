/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { EntidadSaludLabsysUpdateComponent } from 'app/entities/entidad-salud-labsys/entidad-salud-labsys-update.component';
import { EntidadSaludLabsysService } from 'app/entities/entidad-salud-labsys/entidad-salud-labsys.service';
import { EntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';

describe('Component Tests', () => {
    describe('EntidadSaludLabsys Management Update Component', () => {
        let comp: EntidadSaludLabsysUpdateComponent;
        let fixture: ComponentFixture<EntidadSaludLabsysUpdateComponent>;
        let service: EntidadSaludLabsysService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [EntidadSaludLabsysUpdateComponent]
            })
                .overrideTemplate(EntidadSaludLabsysUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EntidadSaludLabsysUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntidadSaludLabsysService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EntidadSaludLabsys(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.entidadSalud = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new EntidadSaludLabsys();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.entidadSalud = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
