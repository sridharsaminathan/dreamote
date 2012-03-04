namespace DreamoteServer
{
    partial class DreamoteServerGui
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DreamoteServerGui));
            this.btn_start_server = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btn_stop_server = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.lbl_port_invalid = new System.Windows.Forms.Label();
            this.txt_port = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.txt_ip = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.notifyIcon1 = new System.Windows.Forms.NotifyIcon(this.components);
            this.contextMenuStrip1 = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.menuItem_restore = new System.Windows.Forms.ToolStripMenuItem();
            this.menuItem_exit = new System.Windows.Forms.ToolStripMenuItem();
            this.checkbox_boot = new System.Windows.Forms.CheckBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.contextMenuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // btn_start_server
            // 
            this.btn_start_server.Location = new System.Drawing.Point(6, 19);
            this.btn_start_server.Name = "btn_start_server";
            this.btn_start_server.Size = new System.Drawing.Size(75, 23);
            this.btn_start_server.TabIndex = 0;
            this.btn_start_server.Text = "Start Server";
            this.btn_start_server.UseVisualStyleBackColor = true;
            this.btn_start_server.Click += new System.EventHandler(this.btn_start_server_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btn_stop_server);
            this.groupBox1.Controls.Add(this.btn_start_server);
            this.groupBox1.Location = new System.Drawing.Point(12, 12);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(279, 62);
            this.groupBox1.TabIndex = 1;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Server Controls";
            // 
            // btn_stop_server
            // 
            this.btn_stop_server.Location = new System.Drawing.Point(119, 19);
            this.btn_stop_server.Name = "btn_stop_server";
            this.btn_stop_server.Size = new System.Drawing.Size(75, 23);
            this.btn_stop_server.TabIndex = 1;
            this.btn_stop_server.Text = "Stop Server";
            this.btn_stop_server.UseVisualStyleBackColor = true;
            this.btn_stop_server.Click += new System.EventHandler(this.btn_stop_server_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.lbl_port_invalid);
            this.groupBox2.Controls.Add(this.txt_port);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Controls.Add(this.txt_ip);
            this.groupBox2.Controls.Add(this.label1);
            this.groupBox2.Location = new System.Drawing.Point(15, 101);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(276, 58);
            this.groupBox2.TabIndex = 2;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Connection Info";
            // 
            // lbl_port_invalid
            // 
            this.lbl_port_invalid.AutoSize = true;
            this.lbl_port_invalid.ForeColor = System.Drawing.Color.Red;
            this.lbl_port_invalid.Location = new System.Drawing.Point(195, 42);
            this.lbl_port_invalid.Name = "lbl_port_invalid";
            this.lbl_port_invalid.Size = new System.Drawing.Size(63, 13);
            this.lbl_port_invalid.TabIndex = 4;
            this.lbl_port_invalid.Text = "Port Invalid!";
            this.lbl_port_invalid.Visible = false;
            // 
            // txt_port
            // 
            this.txt_port.Location = new System.Drawing.Point(197, 18);
            this.txt_port.Name = "txt_port";
            this.txt_port.Size = new System.Drawing.Size(61, 20);
            this.txt_port.TabIndex = 3;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(165, 21);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(26, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Port";
            // 
            // txt_ip
            // 
            this.txt_ip.Enabled = false;
            this.txt_ip.Location = new System.Drawing.Point(36, 18);
            this.txt_ip.Name = "txt_ip";
            this.txt_ip.Size = new System.Drawing.Size(96, 20);
            this.txt_ip.TabIndex = 1;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(13, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(17, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "IP";
            // 
            // notifyIcon1
            // 
            this.notifyIcon1.BalloonTipText = "Dreamote Server";
            this.notifyIcon1.BalloonTipTitle = "Dreamote Title";
            this.notifyIcon1.ContextMenuStrip = this.contextMenuStrip1;
            this.notifyIcon1.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon1.Icon")));
            this.notifyIcon1.Text = "Dreamote Server Gui";
            this.notifyIcon1.Visible = true;
            this.notifyIcon1.DoubleClick += new System.EventHandler(this.notifyIcon1_DoubleClick);
            // 
            // contextMenuStrip1
            // 
            this.contextMenuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.menuItem_restore,
            this.menuItem_exit});
            this.contextMenuStrip1.Name = "contextMenuStrip1";
            this.contextMenuStrip1.Size = new System.Drawing.Size(153, 70);
            // 
            // menuItem_restore
            // 
            this.menuItem_restore.Name = "menuItem_restore";
            this.menuItem_restore.Size = new System.Drawing.Size(152, 22);
            this.menuItem_restore.Text = "Restore";
            this.menuItem_restore.Click += new System.EventHandler(this.menuItem_restore_Click);
            // 
            // menuItem_exit
            // 
            this.menuItem_exit.Name = "menuItem_exit";
            this.menuItem_exit.Size = new System.Drawing.Size(152, 22);
            this.menuItem_exit.Text = "Exit";
            this.menuItem_exit.Click += new System.EventHandler(this.menuItem_exit_Click);
            // 
            // checkbox_boot
            // 
            this.checkbox_boot.AutoSize = true;
            this.checkbox_boot.Location = new System.Drawing.Point(18, 175);
            this.checkbox_boot.Name = "checkbox_boot";
            this.checkbox_boot.Size = new System.Drawing.Size(164, 17);
            this.checkbox_boot.TabIndex = 3;
            this.checkbox_boot.Text = "Run this on Windows Startup";
            this.checkbox_boot.UseVisualStyleBackColor = true;
            this.checkbox_boot.CheckStateChanged += new System.EventHandler(this.checkbox_boot_CheckStateChanged);
            // 
            // DreamoteServerGui
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(306, 262);
            this.Controls.Add(this.checkbox_boot);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "DreamoteServerGui";
            this.Text = "Dreamote Server Gui";
            this.Resize += new System.EventHandler(this.DreamoteServerGui_Resize);
            this.groupBox1.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.contextMenuStrip1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btn_start_server;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox txt_port;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txt_ip;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.NotifyIcon notifyIcon1;
        private System.Windows.Forms.CheckBox checkbox_boot;
        private System.Windows.Forms.Button btn_stop_server;
        private System.Windows.Forms.Label lbl_port_invalid;
        private System.Windows.Forms.ContextMenuStrip contextMenuStrip1;
        private System.Windows.Forms.ToolStripMenuItem menuItem_restore;
        private System.Windows.Forms.ToolStripMenuItem menuItem_exit;
    }
}

