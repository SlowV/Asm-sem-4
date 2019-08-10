$(document).ready(function () {
    $('div[type-layout="include"][name-layout="modal"]').html('<div class="modal fade bd-example-modal-lg" id="modalPreviewArticle" tabindex="-1" role="dialog"' +
        ' aria-labelledby="myLargeModalLabel" aria-hidden="true">' +
        '<div class="modal-dialog modal-xl modal-dialog-scrollable">' +
        '    <div class="modal-content">' +
        '        <div class="modal-header bg-sunny-morning text-white">' +
        '<h5 class="modal-title" id="modalTitle">New message</h5>' +
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '    <span aria-hidden="true">&times;</span>' +
        '</button>' +
        '        </div>' +
        '        <div class="modal-body">' +
        '<input type="hidden" id="urlPre">' +
        '<div class="form-group">' +
        '    <label class="col-form-label slv_label_preview">Title</label>' +
        '    <input type="text" class="form-control slv_input_preview" disabled id="titlePre">' +
        '</div>' +
        '<div class="form-group">' +
        '    <label class="col-form-label slv_label_preview">Description</label>' +
        '    <input class="form-control slv_input_preview" disabled id="descriptionPre">' +
        '</div>' +
        '<div class="form-group p-2">' +
        '    <span class="categoryIdPew"></span>' +
        '</div>' +
        '<div class="form-group">' +
        '    <img src="" id="thumbnailPre" alt="">' +
        '</div>' +
        '<div class="form-group">' +
        '    <label class="col-form-label slv_label_preview">Content</label>' +
        '    <textarea rows="10" name="contentPre"' +
        '  class="form-control slv_input_preview" disabled' +
        '  id="contentPre"></textarea>' +
        '</div>' +
        '<div class="form-group">' +
        '    <label class="col-form-label slv_label_preview">Author</label>' +
        '    <input class="form-control slv_input_preview" disabled id="authorPre">' +
        '</div>' +
        '</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
        '<button type="button" class="btn btn-info" id="btn-preview">Preview</button>' +
        '<button type="button" class="btn btn-warning" id="btn-edit-article-preview">Edit</button>' +
        '<button type="button" class="btn btn-primary" id="btn-save-article">Save</button>' +
        '        </div>' +
        '    </div>' +
        '</div>' +
        '</div>');

    $('div[type-layout="include"][name-layout="header"]').html('<div class="app-header bg-amy-crisp header-shadow header-text-light">' +
        '        <div class="app-header__logo">' +
        '            <div class="logo-src"></div>' +
        '            <div class="header__pane ml-auto">' +
        '                <div>' +
        '                    <button type="button" class="hamburger close-sidebar-btn hamburger--elastic"' +
        '                            data-class="closed-sidebar">' +
        '                            <span class="hamburger-box">' +
        '                                <span class="hamburger-inner"></span>' +
        '                            </span>' +
        '                    </button>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '        <div class="app-header__mobile-menu">' +
        '            <div>' +
        '                <button type="button" class="hamburger hamburger--elastic mobile-toggle-nav">' +
        '                        <span class="hamburger-box">' +
        '                            <span class="hamburger-inner"></span>' +
        '                        </span>' +
        '                </button>' +
        '            </div>' +
        '        </div>' +
        '        <div class="app-header__menu">' +
        '                <span>' +
        '                    <button type="button"' +
        '                            class="btn-icon btn-icon-only btn btn-primary btn-sm mobile-toggle-header-nav">' +
        '                        <span class="btn-icon-wrapper">' +
        '                            <i class="fa fa-ellipsis-v fa-w-6"></i>' +
        '                        </span>' +
        '                    </button>' +
        '                </span>' +
        '        </div>' +
        '        <div class="app-header__content">' +
        '            <div class="app-header-left">' +
        '                <div class="search-wrapper">' +
        '                    <div class="input-holder">' +
        '                        <input type="text" class="search-input" placeholder="Type to search">' +
        '                        <button class="search-icon">' +
        '                            <span></span>' +
        '                        </button>' +
        '                    </div>' +
        '                    <button class="close"></button>' +
        '                </div>' +
        '            </div>' +
        '            <div class="app-header-right">' +
        '                <div class="header-btn-lg pr-0">' +
        '                    <div class="widget-content p-0">' +
        '                        <div class="widget-content-wrapper">' +
        '                            <div class="widget-content-left">' +
        '                                <div class="btn-group">' +
        '                                    <a data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"' +
        '                                       class="p-0 btn">' +
        '                                        <img width="42" class="rounded-circle" src="../images/avatars/1.jpg"' +
        '                                             alt="">' +
        '                                        <i class="fa fa-angle-down ml-2 opacity-8"></i>' +
        '                                    </a>' +
        '                                    <div tabindex="-1" role="menu" aria-hidden="true"' +
        '                                         class="dropdown-menu dropdown-menu-right">' +
        '                                        <button type="button" tabindex="0" class="dropdown-item">User Account</button>' +
        '                                        <button type="button" tabindex="0" class="dropdown-item">En</button>' +
        '                                        <h6 tabindex="-1" class="dropdown-header">Header</h6>' +
        '                                        <button type="button" tabindex="0" class="dropdown-item">Actions</button>' +
        '                                        <div tabindex="-1" class="dropdown-divider"></div>' +
        '                                        <button type="button" tabindex="0" class="dropdown-item">Dividers</button>' +
        '                                    </div>' +
        '                                </div>' +
        '                            </div>' +
        '                            <div class="widget-content-left  ml-3 header-user-info">' +
        '                                <div class="widget-heading">' +
        '                                    SlowV' +
        '                                </div>' +
        '                                <div class="widget-subheading">' +
        '                                    Admin manager' +
        '                                </div>' +
        '                            </div>' +
        '                            <div class="widget-content-right header-user-info ml-3">' +
        '                                <button type="button" class="btn-shadow p-1 btn btn-primary btn-sm show-toastr-example">' +
        '                                    <i class="fa text-white fa-calendar pr-1 pl-1"></i>' +
        '                                </button>' +
        '                            </div>' +
        '                        </div>' +
        '                    </div>' +
        '                </div>' +
        '            </div>' +
        '        </div>' +
        '    </div>')
});